package cn.ch1.endthread;

/**
 * <p>
 *  线程结束:
 *  线程协作式||线程抢占式
 *  个人实践
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/1
 */
public class EndThreadMain {


    /**
     * 抢占式结束线程实践
     * 使用stop强行结束此线程
     * 太过于野蛮,不管你有没有执行完毕直接结束你
     **/
    static class UserThreadSeize implements Runnable{
        @Override
        public void run() {
            System.out.println("当前线程为:"+Thread.currentThread().getName());
            Thread.currentThread().stop();
            while (true){
                System.out.println("线程名称:"+Thread.currentThread().getName());
            }
        }
    }

    /**
     * 协作式结束线程实践
     **/
    static class UserThreadCooperate extends Thread{
        @Override
        public void run() {
            System.out.println("当前线程为:"+Thread.currentThread().getName());
            /**
             *  Thread.interrupted():执行完毕会把结果改为false
             *  isInterrupted():执行完毕不会改结果就是true
             **/
            while (!isInterrupted()){
                System.out.println("线程名称:"+Thread.currentThread().getName());
            }
            System.out.println("循环外部是否结束:"+Thread.interrupted());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        /*UserThreadSeize userThreadSeize=new UserThreadSeize();
        userThreadSeize.run();*/
        UserThreadCooperate userThreadCooperate=new UserThreadCooperate();
        userThreadCooperate.start();
        Thread.sleep(20);
        userThreadCooperate.interrupt();

    }

}
