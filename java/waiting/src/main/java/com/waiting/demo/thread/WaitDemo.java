package com.waiting.demo.thread;

/**
 * @version 1.0
 * @description:
 * @projectName: com.waiting.demo.thread
 * @className: waiting
 * @author:谭农春
 * @createTime:2018/11/12 20:18
 */
public class WaitDemo {

    public static void main(String[] args) throws InterruptedException {
        //wait、notify 只能在同步代码块里用
        final  Object ob  =new Object();
        // 多线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    synchronized (ob) {
                        ob.wait();
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        Thread.sleep(1000);
        synchronized (ob) {
            ob.notify();
        }
        System.out.print("stop the world");
    }
}
