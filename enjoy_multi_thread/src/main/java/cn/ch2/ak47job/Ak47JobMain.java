package cn.ch2.ak47job;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 《wait/notify实现生产者和消费者程序》
 * 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，生产者线程是一个压入线程，
 * 它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * 请实现上面的程序。
 * </p>
 * 案例升级采用多线程处理
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-25
 */
public class Ak47JobMain {

    //弹夹容量
    private static LinkedList<bulletClip> capacity=new LinkedList<>();

    //线程控制器
    private static CountDownLatch countDownLatch=new CountDownLatch(0);


    //子弹夹对象(掌管装弹跟开火)
    static class bulletClip{
        public void out(){
            System.out.println("AK-47已经开火,目前弹夹剩余子弹数量为:" + capacity.size() + "颗");
        }
        public void input(){
            System.out.println("AK-47已经装弹,目前弹夹子弹数量为:" + capacity.size() + "颗");
        }
    }


    static class executionTask implements Runnable{

        private int capacitySize;
        private AtomicInteger output;
        private AtomicInteger input;

        public executionTask(int capacitySize, AtomicInteger output, AtomicInteger input) {
            this.capacitySize = capacitySize;
            this.output = output;
            this.input = input;
        }

        /**开火任务**/
        private void output()
                throws InterruptedException {
            synchronized (capacity){
                while (capacity.isEmpty()){
                    capacity.wait();
                }
                while (capacitySize>0){
                    capacity.removeFirst().out();
                    --capacitySize;
                }
            }
        }

        /**装弹任务**/
        private void input(){
            synchronized (capacity){
                while (capacitySize>0){
                    bulletClip bulletClip=new bulletClip();
                    capacity.addLast(bulletClip);
                    bulletClip.input();
                    --capacitySize;
                }
                capacity.notifyAll();
            }
        }

        @Override
        public void run() {
            try {
                if(capacity.isEmpty()){
                    input();
                    input.incrementAndGet();
                } else {
                    output();
                    output.incrementAndGet();
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args)
            throws InterruptedException {

        //弹夹容量
        int capacitySize=20;
        //最大线程数
        int ThreadSize=50;
        //开火线程计数器,采用原子不然会线程不安全
        AtomicInteger output=new AtomicInteger();
        //装弹线程计数器,采用原子不然会线程不安全
        AtomicInteger input=new AtomicInteger();
        countDownLatch=new CountDownLatch(ThreadSize);
        for (int i = 0; i < ThreadSize; ++i) {
            new Thread(new executionTask(capacitySize,output,input))
                    .start();
        }
        countDownLatch.await();
        System.out.println("总线程数量:"+ThreadSize);
        System.out.println("合计装弹线程数量:"+input.get()+"个");
        System.out.println("合计开火线程数量:"+output.get()+"个");

    }


}
