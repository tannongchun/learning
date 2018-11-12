package com.waiting.demo.gc;

/**
 *  slot
 *  运行 -verbose:gc
 *   不回收内存
 */
public class GC_demo2 {
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }
}
