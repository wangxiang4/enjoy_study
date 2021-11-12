package cn.ch1.job;

/**
 * <p>
 * 《wait/notify实现生产者和消费者程序》
 * 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，生产者线程是一个压入线程，
 * 它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 * 请实现上面的程序。
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-24
 */
public class JobMain {

    static class work{

        //定义一个多线程全局可见统计变量
        private volatile int count=0;
        //子弹最大数量
        private final int MAX_SIZE=20;
        //子弹最小数量
        private final int MIN_SIZE=0;

        //装弹任务
        public synchronized void input() throws InterruptedException {
            while (true) {
                if(count >= MAX_SIZE){
                    notify();
                    wait();
                }
                ++count;
                System.out.println("AK-47已经装弹,目前弹夹子弹数量为:" + count + "颗");
            }
        }

        //开枪任务
        public synchronized void output() throws InterruptedException {
            while (true) {
                if(count == MIN_SIZE){
                    notify();
                    wait();
                }
                --count;
                System.out.println("AK-47已经开火,目前弹夹剩余子弹数量为:" + count + "颗");
            }
        }

    }



    //开枪线程
    static class shot extends Thread{
        private work work;

        public shot(work work) {
            this.work = work;
        }

        @Override
        public void run() {
            try {
                work.output();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    //装弹线程
    static class reload extends Thread{
        private work work;

        public reload(work work) {
            this.work = work;
        }

        @Override
        public void run() {
            try {
                work.input();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        work work=new work();
        shot shot=new shot(work);
        reload reload=new reload(work);
        reload.start();
        shot.start();
    }



}
