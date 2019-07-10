package com.fupeng.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap =new Bootstrap();
            bootstrap.group(boss).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    /**
                     * 不加编码解码处理器无法发送字符串
                     */
                    p.addLast("decoder", new StringDecoder());
                    p.addLast("encoder", new StringEncoder());
                    p.addLast(new ClientHandler());
                }
            }).option(ChannelOption.TCP_NODELAY, true);
            Channel channel =bootstrap.connect("127.0.0.1",8080).sync().channel();
            channel.writeAndFlush("sssss");
            channel.closeFuture().sync();
        }catch (InterruptedException i){

        }finally {
            boss.shutdownGracefully();
        }

    }
}
