package cn.ch4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>
 *
 *  基于AQS 变种CLH队列 同步组件
 *  实现简单的独占读
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class ExclusiveLock implements Lock {


    static class Sync extends AbstractQueuedSynchronizer {


        @Override /** 判断当前锁是否处于占用状态 **/
        protected boolean isHeldExclusively() {
            return getState()>0;
        }

        @Override /** 进行拿锁操作 **/
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0,arg)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }


        @Override /** 进行释放锁操作 **/
        protected boolean tryRelease(int arg) {
            if(getState()==0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(arg);
            return true;
        }

        /** 返回当前锁的线程状态监控条件集合 **/
        Condition newCondition(){
            return new ConditionObject();
        }

    }

    private final Sync sync=new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
        System.out.println(Thread.currentThread().getName()+"已经拿到锁了");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(0);
        System.out.println(Thread.currentThread().getName()+"已经释放锁了");
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }



}
