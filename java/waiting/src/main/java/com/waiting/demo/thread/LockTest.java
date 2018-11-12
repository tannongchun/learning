package com.waiting.demo.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @version 1.0
 * @description:
 * ① LockSupport不需要在同步代码块里 。
 *      所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
 * ② unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
 *
 * @projectName: com.waiting.demo.thread
 * @className: waiting
 * @author:谭农春
 * @createTime:2018/11/12 20:37
 */
public class LockTest {

    public static void main(String[] args)throws Exception {
        Thread A = new Thread(()-> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                LockSupport.park();
                System.out.println(sum);

        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        LockSupport.unpark(A);
    }
}

