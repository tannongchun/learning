package com.kongxiang.disruptor.handler;

import com.kongxiang.disruptor.event.CarEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;



/**
 *  处理器
 *  负责存储车牌号到数据库中
 * */
public class StoreInDBHandler  implements EventHandler<CarEvent>,WorkHandler<CarEvent>{

    @Override
    public void onEvent(CarEvent event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(CarEvent event) throws Exception {
       //获取当前线程id
        long threadid= Thread.currentThread().getId();
        String no = event.getCarNO();
        System.err.println(String.format("线程 %s 正在保存 %s 到数据库中",threadid,no));
    }
}
