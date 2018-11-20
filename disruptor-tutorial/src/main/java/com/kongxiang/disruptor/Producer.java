package com.kongxiang.disruptor;

import com.kongxiang.disruptor.event.CarEvent;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.CountDownLatch;

/**
 *  生产者，主要生产数据
 */
public class Producer implements Runnable {
    // 主线程等待
    private CountDownLatch countDownLatch ;
    private Disruptor<CarEvent> disruptor;
    private static final  int NUM = 10;
    public Producer(CountDownLatch countDownLatch, Disruptor<CarEvent> disruptor) {
        this.countDownLatch = countDownLatch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        // 创建translator
        CarEventTranslator carEventTranslator =new CarEventTranslator();
        try {
            for(int i = 0;i <NUM ;i++){
                    // 发送到disruptor 处理
                    disruptor.publishEvent(carEventTranslator);
                    Thread.sleep(1000);
           }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.err.println("数据生成完成了......");
            countDownLatch.countDown();
        }
    }
}

class CarEventTranslator implements EventTranslator<CarEvent>{

    @Override
    public void translateTo(CarEvent event, long sequence) {
         this.createData(event);
    }

    /**
     *  产生数据
     * @param event
     */
    private void createData(CarEvent event) {
        String no ="赣A-"+ (int)(Math.random()*100000);
        event.setCarNO(no);
        System.out.println(String.format("进来一辆车牌号为%s", no));
    }
}
