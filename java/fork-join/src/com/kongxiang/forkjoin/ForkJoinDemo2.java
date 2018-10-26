package com.kongxiang.forkjoin;

import java.util.concurrent.*;

/**
 * @version 1.0
 * @description:
 * @projectName: com.kongxiang.forkjoin
 * @className: fork-join
 * @author:谭农春
 * @createTime:2018/10/25 18:41
 */
public class ForkJoinDemo2 {

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    long s = System.currentTimeMillis();
    // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    // 提交可分解的PrintTask任务
    ForkJoinTask<Long> sum  = forkJoinPool.submit(new ForkDemo(0, 2000000000));
    //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
   // forkJoinPool.awaitTermination(1, TimeUnit.MILLISECONDS);
    Long num = sum.get();
    long e = System.currentTimeMillis();
    System.err.print("SumDemo1 sum is => "  + num + " take time " +(e-s) +" ms" +" count => " );
    // 关闭线程池
    forkJoinPool.shutdown();
  }


  // fork-join 任务分解
  static class ForkDemo extends RecursiveTask<Long> {

    private static final long MAX = 2000000;

    private long start;
    private long end;

    public ForkDemo(long start, long end) {
      this.start = start;
      this.end = end;
    }

    @Override
    protected Long compute() {
      Long sum = 0L;
      // 当end-start的值小于MAX时候，开始打印
      if ((end - start) <= MAX) {
        for (long i = start; i <= end; i++) {
          sum += i;
        }
        return sum;
      } else {
        //        System.err.println("=====任务分解======");
        // 将大任务分解成两个小任务
        long middle = (start + end) / 2;
        //少算了
        ForkDemo left = new ForkDemo(start, middle-1);
        ForkDemo right = new ForkDemo(middle, end);
        // 并行执行两个小任务
        left.fork();
        right.fork();
        // 把两个小任务累加的结果合并起来
        return left.join() + right.join();
      }
    }
  } // end  classs
}


