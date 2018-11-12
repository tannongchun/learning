package com.waiting.demo.gc;

/**
 *  slot
 *  运行 -verbose:gc
 *   不回收内存
 */
public class GC_demo3 {

    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        // GC回收了内存
        int a= 0;
        System.gc();
    }
}
