package cn.ch4.aqs;

import java.util.concurrent.locks.Lock;

/**
 * <p>
 * 自定义独占锁测试
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class AqsExclusiveMain {


    static class sum{
        static Lock lock=new ExclusiveLock();

        static int i=0;

        public static void accumulate(){
            lock.lock();
            i=i+1;
            lock.unlock();
        }

    }


    public static void main(String[] args) {
        for (int i = 0; i < 1000; ++i) {
            new Thread(()->{
                sum.accumulate();
            }).start();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println("合计:"+sum.i);
    }

}
