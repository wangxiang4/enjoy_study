package cn.ch3.casatomic.basic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 *
 * cas轻量级原子操作类
 * 基础的一些原子操作类使用
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-28
 */
public class BasicMain {

    //CAS原子布尔
    static AtomicBoolean AtomicBoolean=new AtomicBoolean(true);

    //CAS原子Integer
    static AtomicInteger atomicInteger=new AtomicInteger(0);

    //CAS原子Long
    static AtomicLong atomicLong=new AtomicLong(0);


    public static void main(String[] args) {

        for (int i = 0; i < 200; ++i) {
            new Thread(()->{
                //先+1后获取
                atomicInteger.incrementAndGet();
            }).start();
        }

        //由于atomic获取结果没有阻塞这里模拟一个
        //如果当前主线程里面的子线程组还有线程的话我就把当前主线程变为就绪,等待子线程执行完毕先
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println("atomicInteger合计累加值:"+atomicInteger.get());




        for (int i = 0; i < 300; ++i) {
            new Thread(()->{
                atomicLong.incrementAndGet();
            }).start();
        }
        //由于atomic获取结果没有阻塞这里模拟一个
        //如果当前主线程里面的子线程组还有线程的话我就把当前主线程变为就绪,等待子线程执行完毕先
        while (Thread.activeCount()>1){
            Thread.yield();
        }

        System.out.println("atomicLong合计累加值:"+atomicLong.get());

    }


}
