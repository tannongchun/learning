package com.kongxiang.netty.tutorial.runtime;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.runtime
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 21:19
 */
public class ServerHandler  extends ChannelHandlerAdapter {

    private static AtomicInteger count = new AtomicInteger();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("第" +count.getAndIncrement()+ " 次激活 " );

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //网络读写
        Request request = (Request)msg;
        System.out.println("Server : " + request.getId() + ", " + request.getName() + ", " + request.getRequestMessage());

        // 响应客户端
        Response response = new Response();
        response.setId(request.getId());
        response.setName("response" + request.getId());
        response.setResponseMessage("响应内容" + request.getId());
        ctx.writeAndFlush(response);//.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 异常关闭
        ctx.close();
    }

}
