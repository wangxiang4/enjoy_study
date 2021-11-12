package cn.ch2.util.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * <p>
 * 循环屏障实战操作
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-27
 */
public class CyclicBarrierMain {


    //注意一点CyclicBarrier设定的等待触发的线程数要跟目前线程数一致,
    //要不然会出现线程执行完了,CyclicBarrier还处于屏障阻塞阶段
    static  CyclicBarrier cyclicBarrier=new CyclicBarrier(4,new sum());


    //汇报人数,人员是否到齐
    static class sum implements Runnable{
        @Override
        public void run() {
            System.out.println("伙伴们我们人员到齐了,确认无误!");
        }
    }


    static  class work implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+",已经来到了长沙南站!");
                //统计人数是否齐全,等待人齐我们在一起买票进入高铁
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+",已经买票进入高铁站!");
                //再次统计人数是否齐全,等待人齐我们在一起进入列车准备本次的旅途
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+",已经坐上列车,本次G-67列车准备出发,祝大家本次乘坐愉快!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        new Thread(new work(),"喜羊羊").start();
        new Thread(new work(),"懒羊羊").start();
        new Thread(new work(),"小灰灰").start();
        new Thread(new work(),"灰太狼").start();

    }





}
