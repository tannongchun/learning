package com.kongxiang.disruptor.handler;


import com.kongxiang.disruptor.event.CarEvent;
import com.lmax.disruptor.EventHandler;

/**
 *  发送消息到手机
 */
public class SendMessageHandler implements EventHandler<CarEvent> {
    @Override
    public void onEvent(CarEvent event, long sequence, boolean endOfBatch) throws Exception {
        //获取当前线程id
        long threadid= Thread.currentThread().getId();
        String no = event.getCarNO();
        System.err.println(String.format("线程 %s 正在发送短信到车牌号为： %s 车主 ",threadid,no));
    }
}
