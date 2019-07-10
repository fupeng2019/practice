package com.fupeng.netty.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    public static void main(String[] args) {
        //设置bossGroup和工作组。这里可以先理解为两个线程池，bossGroup设置一个线程，用于处理连接请求和建立连接，而工作组线程池大小默认值2 * CPU核数，在连接建立之后处理IO请求
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        //负责读取数据的线程,主要用于读取数据以及业务逻辑处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardServerHandler());
                }
            }) .option(ChannelOption.SO_BACKLOG, 128) .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.SO_REUSEADDR, true).childOption(ChannelOption.TCP_NODELAY, true);
            /* childOption()方法给每条连接设置一些TCP底层相关的属性:
             * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制,true为开启
             * ChannelOption.SO_REUSEADDR表示端口释放后立即就可以被再次使用,因为一般来说,一个端口释放后会等待两分钟之后才能再被使用
             * ChannelOption.TCP_NODELAY表示是否开始Nagle算法,true表示关闭,false表示开启,通俗地说,如果要求高实时性,有数据发送时就马上发送,就关闭,如果需要减少发送次数减少网络交互就开启
             * option()方法给服务端channel设置一些TCP底层相关的属性:
             * ChannelOption.SO_BACKLOG表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁,服务器处理创建新连接较慢,适当调大该参数
             */
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = bootstrap.bind(8080).sync(); // (7)
            // 等待服务器  socket 关闭 。
            //优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
