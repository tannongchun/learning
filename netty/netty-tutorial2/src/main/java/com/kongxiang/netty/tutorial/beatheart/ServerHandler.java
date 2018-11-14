package com.kongxiang.netty.tutorial.beatheart;

import com.kongxiang.netty.tutorial.utils.Utils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

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

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if(msg instanceof  String){
                System.err.println("[Server Receive ]>>>" + msg + " client " + count.getAndIncrement());
                // 客户端响应
                ctx.writeAndFlush("pong " + Utils.currentMS());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
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
