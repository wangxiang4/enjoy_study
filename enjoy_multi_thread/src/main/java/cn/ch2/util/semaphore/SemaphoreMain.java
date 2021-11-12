package cn.ch2.util.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * 信息量实战
 * 对于流控方面用的很多,限制只能由几个线程去执行比如数据库连接池
 *
 * 本次实战使用semaphore模拟线程一个线程池拿连接
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-27
 */
public class SemaphoreMain {


    static class SemaphoreDBPool{

        LinkedList<Connection> connections=new LinkedList<>();

        //这里我用了两个Semaphore是因为这里有个坑,使用Semaphore归还许可证时可以无限归还
        //所以设置范围区间,只能在这个范围区间活动
        Semaphore max,min;

        public void initializePoolSize(int size){
            max=new Semaphore(size);
            min=new Semaphore(0);
            while (size>0){
                connections.addLast(new SqlConnection());
                --size;
            }
        }


        public Connection getConnection()
                throws InterruptedException {
            //交叉扭转范围区间逻辑
            max.acquire();
            Connection connection;
            synchronized (connections){
                connection=connections.removeFirst();
            }
            min.release();
            return connection;
        }

        public void returnConnection(Connection connection)
                throws InterruptedException {
            //交叉扭转范围区间逻辑
            min.acquire();
            synchronized (connections){
                connections.addLast(connection);
            }
            max.release();
        }

    }


    static class work implements Runnable{

        SemaphoreDBPool semaphoreDBPool;

        public work(SemaphoreDBPool semaphoreDBPool) {
            this.semaphoreDBPool = semaphoreDBPool;
        }

        @Override
        public void run() {
            try {
                Connection connection=semaphoreDBPool.getConnection();
                System.out.println(Thread.currentThread().getName()+"线程,已经拿到数据库连接!");
                connection.commit();
                semaphoreDBPool.returnConnection(connection);
                System.out.println(Thread.currentThread().getName()+"线程,已经归还数据库连接!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {

        SemaphoreDBPool semaphoreDBPool=new SemaphoreDBPool();
        //设置10个连接池数量
        semaphoreDBPool.initializePoolSize(10);
        //启动20个线程获取数据库连接
        for (int i = 0; i < 20; ++i) {
            new Thread(new work(semaphoreDBPool),"线程"+(i+1)).start();
        }

    }



}
