package com.kongxiang.netty.tutorial.beatheart;

import com.kongxiang.netty.tutorial.utils.Utils;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.netty.tutorial.beatheart
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 22:05
 */
public class TestTimerTask {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(new HeartBeatTask(),0, 2, TimeUnit.SECONDS);
    }

     static class HeartBeatTask implements Runnable {

        @Override
        public void run() {
            String msg = "ping " + Utils.currentMS();
            System.err.println(msg);
        }
    }
}
