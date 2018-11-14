package com.kongxiang.netty.tutorial.beatheart;

import com.kongxiang.netty.tutorial.utils.Utils;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.Calendar;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler extends ChannelHandlerAdapter {

	private static AtomicInteger count = new AtomicInteger();
	/**
	 * 创建定时任务线程池
     */
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private ScheduledFuture<?> heartBeat;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			System.err.println("[Client x ] >>> " + msg + "  client " + count.getAndIncrement());
			if( count.getAndIncrement() ==1 ){
				// 每隔10秒检测一次
				this.heartBeat = this.scheduler.scheduleWithFixedDelay(new HeartBeatTask(ctx),0, 2, TimeUnit.SECONDS);
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
		cause.printStackTrace();
		if (heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	static class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;
		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx =ctx;
		}

		@Override
		public void run() {
			String msg = "ping " + Utils.currentMS();
			this.ctx.writeAndFlush(msg);
		}
	}
}
