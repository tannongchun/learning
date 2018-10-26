package com.kongxiang.hprof;

public class HprofSample {
    // 测试方法1
    public void slowActionl() {
        try {
            System.out.println(" Action 1 is run.");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试方法二
    public void slowAction2() {
        try {
            System.out.println(" Action 2 is run.");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 主函数运行
    public static void main(String[] args) {
        HprofSample hprofSample = new HprofSample();
        hprofSample.slowActionl();
        hprofSample.slowAction2();
    }

}