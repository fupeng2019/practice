/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.fupeng.netty.rpc;

import com.fupeng.netty.rpc.api.NumberService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Modification of which utilizes Java object serialization.
 */
public final class ObjectEchoClient {


    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public  ObjectEchoClientHandler objectEchoClientHandler;

    private static Channel channel;

    public void init() throws Exception {
        objectEchoClientHandler =new ObjectEchoClientHandler();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            objectEchoClientHandler);
                }
             }).option(ChannelOption.TCP_NODELAY, true);

            // Start the connection attempt.
            channel = b.connect(HOST, PORT).sync().channel();

        } finally {
            //group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        NumberService numberService=(NumberService)Proxy.newProxyInstance(NumberService.class.getClassLoader(), new Class[]{NumberService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ObjectEchoClient ObjectEchoClient =new ObjectEchoClient();
                ObjectEchoClient.init();
                MsgObject msgObject =new MsgObject(NumberService.class.getName(),method.getName(),method.getParameterTypes(),args);
                channel.writeAndFlush(msgObject);
                return ((MsgObject)ObjectEchoClient.objectEchoClientHandler.getObject()).getMessage();
            }
        });
        System.out.println(numberService.getCode(100L));
        channel.closeFuture().sync();
    }
}
