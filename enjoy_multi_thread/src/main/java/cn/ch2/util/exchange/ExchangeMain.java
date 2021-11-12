package cn.ch2.util.exchange;

import java.util.concurrent.Exchanger;

/**
 * <p>
 *
 * Exchange实现两个线程数据互换,只能同时针对两个线程的数据互换
 *
 * 实战:
 * 小明本来没有犯法,是个好人
 * 小霸王犯了法要坐牢,是个坏人
 * 此时小霸王以小明家人做威胁,逼迫小明去帮小霸王坐牢
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-27
 */
public class ExchangeMain {

    //定义ExChanger线程交换数据对象
    private static Exchanger<String> exchanger=new Exchanger<>();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                //小明的数据
                String str=":我不认罪,我没有杀人,冤枉啊!";
                //线程交换数据
                str=exchanger.exchange(str);
                //获取数据交换后的数据
                System.out.println(Thread.currentThread().getName()+str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"小明").start();

        new Thread(()->{
            try {
                //小霸王的数据
                String str=":我认罪,人就是我杀的,枪毙我啊!";
                //线程交换数据
                str=exchanger.exchange(str);
                //获取数据交换后的数据
                System.out.println(Thread.currentThread().getName()+str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"小霸王").start();

    }

}
