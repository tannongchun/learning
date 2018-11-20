package com.kongxiang.disruptor;

import com.kongxiang.disruptor.event.CarEvent;
import com.kongxiang.disruptor.handler.SendMessageHandler;
import com.kongxiang.disruptor.handler.StoreInDBHandler;
import com.kongxiang.disruptor.handler.TellMQHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *  主程序
 */
public class DisruptorApp {

    public static void main(String[] args) {
        try {
            int bufferSize =2048;
            ExecutorService executor =Executors.newFixedThreadPool(1);

            ThreadFactory threadFactory =  Executors.defaultThreadFactory();
            // 1.创建 Disruptor
            Disruptor<CarEvent> disruptor = new Disruptor<CarEvent>(
                    new EventFactory<CarEvent>() {
                        @Override
                        public CarEvent newInstance() {
                            return new CarEvent();
                        }
                    },
                    bufferSize,
                    threadFactory,
                    ProducerType.MULTI,
                    new YieldingWaitStrategy()
            );
           // 2. 组合处理链式
            EventHandlerGroup<CarEvent> handlerGroup = disruptor.handleEventsWith(new StoreInDBHandler(),new TellMQHandler());
            handlerGroup.then(new SendMessageHandler());
            //3.启动
            disruptor.start();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            // 4.生产测试数据
            executor.submit(new Producer(countDownLatch,disruptor));
            countDownLatch.await();
             System.err.println("Disruptor finish ....");
               //关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
             disruptor.shutdown();
             executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
