package cn.ch2.forkjoin.workSecret;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <p>
 * 模拟ForkJoin内部的工作密取过程
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-26
 */
public class WorkSecretMain {

    //定义任务对象
    static class workTask {
        public void execution(String name){
            System.out.println("任务执行完毕,"+name);
        }
    }

    //定义消费者跟生成者对象,用来模拟工作密取
    static class consumersAndProducers implements Runnable{

        LinkedBlockingDeque<workTask> producers;
        LinkedBlockingDeque<workTask> consumers;

        public consumersAndProducers(LinkedBlockingDeque<workTask> producers, LinkedBlockingDeque<workTask> consumers) {
            this.producers = producers;
            this.consumers = consumers;
        }

        @Override
        public void run() {
            try {
                while (true){
                    Random random=new Random();
                    //创建随机bool实现工作密取控制
                    if(random.nextBoolean()){
                        for (int i = 0; i < random.nextInt(5); ++i) {
                            producers.putLast(new workTask());
                        }
                    }
                    //如果生成队列为空,就让消费线程密取生成队列的任务
                    if(producers.isEmpty()){
                        if(!consumers.isEmpty()){
                            consumers.takeLast().execution("由消费线程密取从尾巴拿执行");
                        }
                    }else{
                        producers.takeFirst().execution("由生产线程正常从头部拿执行");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        //创建双向链阻塞队列,因为他可以实现工作密取的功能ForkJoin内部用的也是他
        LinkedBlockingDeque<workTask> producers=new LinkedBlockingDeque<>();
        LinkedBlockingDeque<workTask> consumers=new LinkedBlockingDeque<>();

        //下面这四个线程主要实现线程并发
        //进入if(!consumers.isEmpty())这个工作密取判断时,把消费队列变成生产队列
        //实现消费线程帮生产线程执行任务,就从尾巴拿执行
        new Thread(new consumersAndProducers(producers,consumers)).start();
        new Thread(new consumersAndProducers(producers,consumers)).start();

        new Thread(new consumersAndProducers(consumers,producers)).start();
        new Thread(new consumersAndProducers(consumers,producers)).start();

    }


}
