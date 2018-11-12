package com.waiting.demo.waitdemo;

/**
 * @version 1.0
 * @description:
 *
 *          javap -l -v 类名.class
 *
 * @projectName: com.waiting.demo.waitdemo
 * @className: waiting
 * @author:谭农春
 * @createTime:2018/11/12 20:03
 */
public class TestWait {
    public static void main(String[] args) {
        TestWait wait = new TestWait();
        try {
            wait.wait(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
