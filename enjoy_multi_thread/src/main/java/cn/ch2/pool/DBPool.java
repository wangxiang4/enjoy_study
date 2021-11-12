package cn.ch2.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * <p>
 * 实现一个等待超时模式实现一个连接池
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-25
 */
public class DBPool {

    //存放数据连接信息
    private static  LinkedList<Connection> connections=new LinkedList<>();



    //初始化连接池的最大连接数
    public DBPool(int initializationNumber) {
        for (int i = 0; i < initializationNumber; ++i) {
            connections.add(SqlConnection.getSqlConnection());
        }
    }



    //归还连接
    public void returnConnection(Connection connection){
        synchronized (connections){
            connections.addLast(connection);
            connections.notifyAll();
        }
    }



    //设置超时并且获取连接
    public Connection fetchConnection(long timeout)
            throws InterruptedException {
       synchronized (connections){
           //是否设置连接超时(-1永久)
           if(timeout<0){
               while (connections.isEmpty()){
                   connections.wait();
               }
               return connections.removeFirst();
           }else{
               //记录当前时间加上超时时间的总和时间戳
               Long recordTime=System.currentTimeMillis()+timeout;
               while (connections.isEmpty()&&timeout<0){
                   connections.wait(timeout);
                   //重新统计时间戳
                   recordTime=recordTime-System.currentTimeMillis();
               }
               //如果当前有连接可用就返回没有连接可用返回null
               Connection connection=null;
               if(!connections.isEmpty()){
                    connection=connections.removeFirst();
               }
               return connection;
           }
       }

    }



}
