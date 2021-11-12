package cn.ch1.syn;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/3
 */
public class SynchronizedLock {


    public static  void  ObjectSynchronizeLock(){
        SynchronizedLock synchronizedLock=new SynchronizedLock();
        synchronized(synchronizedLock){
            synchronizedLock.notify();
            System.out.println("对象锁定");
        }
    }

    public static  void  ClassSynchronizeLock(){
        synchronized(SynchronizedLock.class){
            System.out.println("对象锁定");
        }
    }

    public static void main(String[] args) {
        //启动10个线程
        for (int i = 0; i <10 ; ++i) {
            new Thread(()->ObjectSynchronizeLock()).start();
        }

    }



}
