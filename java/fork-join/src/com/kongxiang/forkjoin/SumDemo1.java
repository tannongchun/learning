package com.kongxiang.forkjoin;

/**
 * @version 1.0
 * @description:
 *   计算
 * @projectName: com.kongxiang.forkjoin
 * @className: fork-join
 * @author:谭农春
 * @createTime:2018/10/25 18:32
 */
public class SumDemo1 {
   private  static  final  Integer NUM = 2000000000;
  public static void main(String[] args) {

    long s = System.currentTimeMillis();
    long  sum = 0;
    for(int i=1;i<=NUM;i++){
      sum = sum+i;
    }
    long e = System.currentTimeMillis();
    // 打印计算结果
    System.err.print("SumDemo1 sum is => "  + sum + " take time " +(e-s) +" ms");
  }

}
