package cn.ch3.casatomic.job;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *
 * 小明中了彩票享受生活去了，他遗留了一个类，是个计数器的功能，现在请你完成这个计数器。
 * 在这个类里，他已经完成了线程安全的get方法和compareAndSet()方法，请你用循环CAS机制完成它。
 * 完成后自行启动多个线程测试是否能正常工作。
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-28
 */
public class AtomicJobMain {

    static AtomicInteger atomicInteger=new AtomicInteger();

    private static void compareAndSet(){
        for (;;){
            int i=atomicInteger.get();
            boolean b=atomicInteger.compareAndSet(i,++i);
            if(b){
                break;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            new Thread(()->{
                for (int j = 0; j < 100; ++j) {
                    compareAndSet();
                }
           }).start();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println(atomicInteger.get());
    }


}
