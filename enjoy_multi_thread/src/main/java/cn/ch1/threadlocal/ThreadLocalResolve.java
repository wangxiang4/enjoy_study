package cn.ch1.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/3
 */
public class ThreadLocalResolve {

    private static final Integer THREAD_SIZE=500;

    final static ExecutorService executorService= new ThreadPoolExecutor(5,5,
            1,TimeUnit.MINUTES,new LinkedBlockingQueue<>());

    final static ThreadLocal<ByteObj> color=new ThreadLocal<>();

    //创建一个5M的字节流对象
    static class ByteObj{
        private Byte[] bytes=new Byte[1024*1024*5];
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.execute(()->{
                color.set(new ByteObj());
                System.out.println("创建5M字节流");
                color.remove();
            });
            Thread.sleep(100);
        }
    }

}
