package cn.ch1.deadlock;

/**
 * <p>
 *  测试多线程睡眠不会被唤醒
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/3
 */
public class Deadlock {


    public static void main(String[] args) {
        Deadlock deadlock=new Deadlock();

        new Thread(()->{
            System.out.println("线程名称:A");
            synchronized (deadlock){
                try {
                    System.out.println(Thread.currentThread().getName()+"线程名称:A进入唤醒");
                    deadlock.notify();
                    System.out.println("线程名称:A进入等待");
                    deadlock.wait();
                    System.out.println("我叫小狗");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("线程名称:B");
            synchronized (deadlock){
                try {
                    System.out.println("线程名称:B进入等待");
                    deadlock.wait();
                    System.out.println("我叫小猪");
                    System.out.println("线程名称:B进入唤醒");
                    deadlock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
