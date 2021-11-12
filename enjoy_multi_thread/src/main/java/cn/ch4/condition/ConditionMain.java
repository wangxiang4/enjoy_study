package cn.ch4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 使用Lock显示锁的 Condition 线程状态监控管理类
 * </p>
 *
 *
 * 挣钱案例
 * 我帮老板打完一天工,我是按天结算
 * 做完事情需要跟老板要钱
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class ConditionMain {



    Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();

    public int money=0;

    public void labor(){
        lock.lock();
        try {
            while (money==0){
                condition.await();
            }
            System.out.println("感谢老板大发慈悲,微信红包已到账:"+money+"元");
            money=0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void boss() {
        lock.lock();
        money=100;
        System.out.println("农民工同志,我大发慈悲的给你发工资了,你点一下红包!");
        condition.signalAll();
        lock.unlock();
    }


    public static void main(String[] args) {

        ConditionMain conditionMain=new ConditionMain();
        for (int i = 0; i < 100; ++i) {
            new Thread(()->{
                if(conditionMain.money==0){
                    conditionMain.boss();
                }else{
                    conditionMain.labor();
                }
            }).start();
        }

    }


}
