package cn.ch4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>
 *
 * 基于AQS 变种CLH队列 同步组件
 * 实现简单的共享读
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-29
 */
public class SharedLock implements Lock {

    //具体实现最多同时是能有5个线程可以拿到锁
    static class Sync extends AbstractQueuedSynchronizer{

        /** 设置最大线程数量 **/
        protected Sync(int max) {
            if(max <= 0){
                throw new IllegalMonitorStateException();
            }
            setState(max);
        }

        @Override /** 采用自旋拿取锁操作 **/
        protected int tryAcquireShared(int arg) {
            for (;;){
                int state=getState();
                int modify=state-arg;
                if(modify<0 || compareAndSetState(state,modify)){
                    return modify;
                }
            }
        }

        @Override /** 采用自旋释放锁操作 **/
        protected boolean tryReleaseShared(int arg) {
            for (;;){
                int state=getState();
                int modify=state+arg;
                if(compareAndSetState(state,modify)){
                    return true;
                }
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()>0;
        }

        Condition newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync=new Sync(5);


    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }



}
