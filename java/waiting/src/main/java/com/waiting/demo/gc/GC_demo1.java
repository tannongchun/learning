package com.waiting.demo.gc;

/**
 *  slot
 *  运行 -verbose:gc
 */
public class GC_demo1 {

    public static void main(String[] args) {
        byte[] placeholder = new byte[64 * 1024 * 1024];
        System.gc();
        /**
         * [GC (System.gc())  68864K->66208K(125952K), 0.0020279 secs]
          [Full GC (System.gc()) Disconnected from the target VM, address: '127.0.0.1:63645', transport: 'socket'
         66208K->66127K(125952K), 0.0195674 secs]
         */
    }
}
