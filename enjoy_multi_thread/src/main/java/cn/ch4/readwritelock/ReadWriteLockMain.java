package cn.ch4.readwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * 使用显示Lock中的读写锁
 * 读为独占锁
 * 写为共享锁
 * 并且读写两个锁互斥,读时候不能写,写的时候不能读
 *
 * 线程比例配置
 * 10/1比例 读10 写1
 *
 * </p>
 *
 *
 * 本次实例为商品:立白洗衣液
 *
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class ReadWriteLockMain {


    static Commodity commodity=new Commodity();

    static ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    static Lock readLock=readWriteLock.readLock();
    static Lock writeLock=readWriteLock.writeLock();
    static int readThreadSize=10;
    static int writeThreadSize=3;

    //测试重入独占读
    static Lock lock=new ReentrantLock();

    public static void selectLength(){
        readLock.lock();
        try {
            Thread.sleep(100);
            System.out.println("当前商品"+commodity.getName()+"数量为:"+commodity.getLength());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }

    public static void writeLength(){
        writeLock.lock();
        try {
            Thread.sleep(100);
            commodity.setLength(commodity.getLength()-1);
            System.out.println("已购买当前商品"+commodity.getName()+",剩余数量为:"+commodity.getLength());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        commodity.setName("立白洗衣粉.这个东西特么真好用");
        commodity.setLength(3);
        for (int i = 0; i < writeThreadSize; ++i) {
            new Thread(()->{
                long recordTime=System.currentTimeMillis();
                writeLength();
                System.out.println("当前线程运行总时长:"+(System.currentTimeMillis()-recordTime));
            }).start();
            for (int j = 0; j < readThreadSize; ++j) {
                new Thread(()->{
                    long recordTime1=System.currentTimeMillis();
                    selectLength();
                    System.out.println("当前线程运行总时长:"+(System.currentTimeMillis()-recordTime1));
                }).start();;
            }
        }
    }



}
