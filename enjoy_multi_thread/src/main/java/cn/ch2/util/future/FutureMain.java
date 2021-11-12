package cn.ch2.util.future;

import java.util.concurrent.FutureTask;

/**
 * <p>
 * Callable,Future和FutureTask实战
 *
 * Future具体作用:可以让多线程执行有返回结果集
 *
 * 具体实现:
 * 模拟现实场景,比如微服务,我有3个系统每个系统是独立的,不同的
 * 现在我要去
 * 系统A拿出一个数据10
 * 系统B拿出一个数据20
 * 系统C拿出一个数据30
 *
 * 并且中断系统A任务,
 * 最后在主线程进行B,C合并统计
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-27
 */
public class FutureMain {


    public static void main(String[] args)
            throws Exception{

        FutureTask<Integer>  systemATask=new FutureTask(()->{
            //具体Callable返回
            while (true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("获取系统A任务已经被终止!");
                    return 0;
                }
            }
        });

        FutureTask<Integer>  systemBTask=new FutureTask(()->{
            //具体Callable返回
            return 20;
        });

        FutureTask<Integer>  systemCTask=new FutureTask(()->{
            //具体Callable返回
            return 30;
        });

        new Thread(systemATask).start();
        new Thread(systemBTask).start();
        new Thread(systemCTask).start();


        //中断系统A任务,内部采用线程协调模式终止
        Thread.sleep(300);
        systemATask.cancel(true);

        int sum=systemBTask.get()+systemCTask.get();

        System.out.println("成功获取了各自系统中的数据,合计结果为:"+sum);


    }


}
