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
import com.fupeng.netty.rpc.api.impl.NumberServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

    public static Map<String,Object> map =new ConcurrentHashMap<String,Object>();

    {
        NumberService numberService =new NumberServiceImpl();
        map.put(NumberService.class.getName(),numberService);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        // Echo back the received object to the client.
        //ctx.write(msg);
        MsgObject msgObject =(MsgObject) msg;
        if(msgObject.getClassName() !=null&&msgObject.getClassName()!=""){
            Object object=map.get(msgObject.getClassName());
            //调用方法
            Object result=object.getClass().getMethod(msgObject.getMethodName(),msgObject.getParameterTypes()).invoke(object,msgObject.getArgs());
            ctx.writeAndFlush(new MsgObject(result));
            System.out.println("111111111111111111");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
