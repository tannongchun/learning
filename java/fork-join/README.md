#FORK JOIN (并行编程)

## 并行 与 并发的区别 

-  并发：交替执行
-  并行：同时执行.

##  并行运行的线程数说明

-  其默认线程数为处理器数量,Runtime.getRuntime().availableProcessors()
-  不过也可以修改这个值,但是是全局修改,对所有的并行流有效
-  System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");

## 工作窃取(work-stealing)算法
