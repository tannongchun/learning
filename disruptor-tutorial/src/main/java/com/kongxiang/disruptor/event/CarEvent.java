package com.kongxiang.disruptor.event;

import java.io.Serializable;

/**
 *  汽车事件
 * */
public class CarEvent implements Serializable{
    // 车牌号
  private String carNO;

    public String getCarNO() {
        return carNO;
    }
    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }
}
