package cn.ch2.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 测试模拟等待超时数据库连接池
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-25
 */
public class DBTest {

    private static DBPool dbPool=new DBPool(0);
    private static CountDownLatch countDownLatch=new CountDownLatch(0);

    public static void main(String[] args)
            throws InterruptedException {

        //线程总数
        int ThreadCount=50;
        countDownLatch=new CountDownLatch(ThreadCount);
        //连接池大小
        int DBSize=20;
        dbPool=new DBPool(DBSize);
        AtomicInteger yes=new AtomicInteger();
        AtomicInteger on=new AtomicInteger();
        for (int i = 0; i < ThreadCount; ++i) {
            new Thread(new work(DBSize,yes,on))
            .start();
        }
        countDownLatch.await();
        System.out.println("总线程数:"+ThreadCount*DBSize);
        System.out.println("拿到数据库连接的线程数:"+yes.get());
        System.out.println("没有拿到数据库连接的线程数:"+on.get());

    }


    static class work implements Runnable{

        //循环次数
        private int count;
        AtomicInteger yes;
        AtomicInteger on;

        public work(int count, AtomicInteger yes, AtomicInteger on) {
            this.count = count;
            this.yes = yes;
            this.on = on;
        }

        @Override
        public void run() {
            while (count>0){
                try {
                    Connection connection=dbPool.fetchConnection(600);
                    if(connection!=null){
                        System.out.println("拿到数据库连接开始提交");
                        yes.incrementAndGet();
                        connection.commit();
                        dbPool.returnConnection(connection);
                    }else{
                        on.incrementAndGet();
                        System.out.println("等待超时!");
                    }
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }finally {
                    --count;
                }
            }
            countDownLatch.countDown();
        }

    }


}
