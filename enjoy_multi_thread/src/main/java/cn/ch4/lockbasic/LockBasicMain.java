package cn.ch4.lockbasic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 显示锁的基本使用
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class LockBasicMain {

    //使用重入锁(递归锁)
    private static Lock lock=new ReentrantLock();

    static int i=0;

    private static void accumulate() throws InterruptedException {
        lock.lock();
        for (int j = 0; j < 1000; ++j) {
            i=i+1;
        }
        lock.unlock();
    }


    public static void main(String[] args) {
        for (int j = 0; j < 10; ++j) {
            new Thread(()->{
                try {
                    accumulate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println("合计:"+i);
    }

}
