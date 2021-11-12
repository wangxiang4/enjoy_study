package cn.ch2.util.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 发令枪-实战
 * 适用:等待子线程处理完毕然后主线程在运行的逻辑场景
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-27
 */
public class CountDownLatchMain {

    //这里的计算初始化计算数可以随意设置,只需要后期在线程中控制减到0即刻,
    //可以跟线程数设置不一致没关系的
    static CountDownLatch startingGun=new CountDownLatch(8);


    static class work implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"跑完第一圈");
            startingGun.countDown();
            System.out.println(Thread.currentThread().getName()+"跑完第二圈");
            startingGun.countDown();
        }
    }

    /** 团队挨罚案例 **/
    public static void main(String[] args)
            throws InterruptedException {

        new Thread(new work(),"小明").start();
        new Thread(new work(),"小张").start();
        new Thread(new work(),"小王").start();
        new Thread(new work(),"小狗").start();
        startingGun.await();
        System.out.println("全部挨罚完毕,全体都有解散!");
    }


}
