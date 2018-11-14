package com.kongxiang.netty.tutorial.beatheart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.runtime
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 21:17
 */
public class BeatServer {

    public static void main(String[] args) throws Exception{
         // 一个是用于处理服务器端接收客户端连接的
        // 一个是进行网络通信的（网络读写的）
        EventLoopGroup pGroup = new NioEventLoopGroup();
        EventLoopGroup cGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        // 工作线程组和 连接线程组
        b.group(pGroup, cGroup)
                .channel(NioServerSocketChannel.class) // 指定NIO的模式.NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置TCP缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024) // 设置发送缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024) // 这是接收缓冲大小
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                //设置日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        sc.pipeline().addLast(new ServerHandler());
                    }
                });

        ChannelFuture cf = b.bind(8765).sync();
        // 阻塞等待退出
        cf.channel().closeFuture().sync();
        // 释放线程组资源
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();

    }
}
