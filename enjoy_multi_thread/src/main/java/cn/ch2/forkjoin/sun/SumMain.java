package cn.ch2.forkjoin.sun;

import cn.ch2.forkjoin.MakeArray;

/**
 * <p>
 *
 * 主线程累加
 * 不采用ForkJoin框架做处理
 * 用来做比较时间差异
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-26
 */
public class SumMain {

    public static void main(String[] args) {

        int[] array= MakeArray.make();
        int sum=0;
        System.out.println("开始计时!");
        long recordTime=System.currentTimeMillis();
        for (int i = 0; i < array.length; ++i) {
            sum+=array[i];
        }
        System.out.println("数组累加最终结果:"+sum+",耗时时长:"+(System.currentTimeMillis()-recordTime)+"ms");

    }

}
