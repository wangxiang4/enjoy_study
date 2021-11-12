package cn.ch4.aqs;

import java.util.concurrent.locks.Lock;

/**
 * <p>
 * 自定义共享锁测试
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class AqsSharedLockMain {


    static class sum{
        static Lock lock=new SharedLock();

        static int i=1000;


        public static void accumulate(){
            lock.lock();
            try {
                Thread.sleep(1000);
                System.out.println("我中将了得了"+i+"块");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; ++i) {
            new Thread(()->{
                long recordTime=System.currentTimeMillis();
                sum.accumulate();
                System.out.println("当前线程读取消耗的时间:"+(System.currentTimeMillis()-recordTime));
            }).start();
        }
    }

}
