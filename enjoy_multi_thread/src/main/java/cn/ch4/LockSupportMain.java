package cn.ch4;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class LockSupportMain {

    public static void main(String[] args) throws InterruptedException {

        //这是基于操作系统锁级别的操作,可以进行先唤醒后睡眠,线程还是不会阻塞
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("成功输出");

    }

}
