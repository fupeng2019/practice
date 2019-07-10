package com.fupeng.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

/**
 *
 * 要启动Netty服务端,必须要指定三类属性,分别是线程模型、IO模型、连接读写处理逻辑
 * Netty服务端启动的流程是创建引导类给引导类指定线程模型,IO模型,连接读写处理逻辑,绑定端口之后服务端就启动起来
 * bind方法是异步的通过异步机制来实现端口递增绑定
 * Netty服务端启动额外的参数,主要包括给服务端channel或者channel设置属性值,设置底层TCP参数
 */

public class Server {

    private static final int BEGIN_PORT = 8080;
    private static final AttributeKey<Object> SERVER_NAME_KEY = AttributeKey.newInstance("serverName");
    private static final String SERVER_NAME_VALUE = "nettyServer";
    public static final AttributeKey<Object> CLIENT_KEY = AttributeKey.newInstance("clientKey");
    public static final String CLIENT_VALUE = "clientValue";

    /**
     * 创建两个NioEventLoopGroup,这两个对象可以看做是传统IO编程模型的两大线程组,boosGroup表示监听端口,创建新连接的线程组,workerGroup表示处理每一条连接的数据读写的线程组
     * 创建引导类 ServerBootstrap进行服务端的启动工作,通过.group(boosGroup, workerGroup)给引导类配置两大线程定型引导类的线程模型指定服务端的IO模型为NIO,通过.channel(NioServerSocketChannel.class)来指定IO模型
     * 调用childHandler()方法给引导类创建ChannelInitializer定义后续每条连接的数据读写,业务处理逻辑,泛型参数NioSocketChannel是Netty对NIO类型的连接的抽象,而NioServerSocketChannel也是对NIO类型的连接的抽象
     * serverBootstrap.bind()是异步的方法调用之后是立即返回的,返回值是ChannelFuture,给ChannelFuture添加监听器GenericFutureListener,在GenericFutureListener的operationComplete方法里面监听端口是否绑定成功
     * childHandler()用于指定处理新连接数据的读写处理逻辑,handler()用于指定在服务端启动过程中的一些逻辑
     * attr()方法给服务端的channel即NioServerSocketChannel指定一些自定义属性,通过channel.attr()取出该属性,给NioServerSocketChannel维护一个map
     * childAttr()方法给每一条连接指定自定义属性,通过channel.attr()取出该属性
     * childOption()方法给每条连接设置一些TCP底层相关的属性:
     * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制,true为开启
     * ChannelOption.SO_REUSEADDR表示端口释放后立即就可以被再次使用,因为一般来说,一个端口释放后会等待两分钟之后才能再被使用
     * ChannelOption.TCP_NODELAY表示是否开始Nagle算法,true表示关闭,false表示开启,通俗地说,如果要求高实时性,有数据发送时就马上发送,就关闭,如果需要减少发送次数减少网络交互就开启
     * option()方法给服务端channel设置一些TCP底层相关的属性:
     * ChannelOption.SO_BACKLOG表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁,服务器处理创建新连接较慢,适当调大该参数
     *
     */
    public  void init() throws Exception{
        //设置bossGroup和工作组。这里可以先理解为两个线程池，bossGroup设置一个线程，用于处理连接请求和建立连接，而工作组线程池大小默认值2 * CPU核数，在连接建立之后处理IO请求
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        //负责读取数据的线程,主要用于读取数据以及业务逻辑处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).handler(new ChannelInitializer<ServerSocketChannel>() {
                @Override
                protected void initChannel(ServerSocketChannel ch) throws Exception {
                    System.out.println("服务端启动中");
                    ch.pipeline().addLast(new StringEncoder());

                    System.out.println(ch.attr(SERVER_NAME_KEY).get());
                }
            }).attr(SERVER_NAME_KEY, SERVER_NAME_VALUE)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childAttr(CLIENT_KEY, CLIENT_VALUE)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    /***
                     * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
                     * 他的目的是帮助使用者配置一个新的Channel。
                     * 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
                     * 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
                     * 然后提取这些匿名类到最顶层的类上。
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                            System.out.println(ch.attr(CLIENT_KEY).get());
                        }
                    });
            /***
             * 绑定端口并启动去接收进来的连接
             */
            ChannelFuture f = bootstrap.bind(BEGIN_PORT).sync();

            f.addListener(future->{
                if(future.isSuccess()){
                    System.out.println("success");
                }else {
                    System.out.println("fail");
                }

                if(future.isCancelled()){
                    System.out.println("cancelled");
                }
            });

            /**
             * 这里会一直等待，直到socket被关闭
             */
            f.channel().closeFuture().sync();
        }finally {
            /***
             * 关闭
             */
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new Server().init();
    }
}
