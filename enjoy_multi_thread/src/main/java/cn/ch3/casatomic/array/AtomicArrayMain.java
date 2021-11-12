package cn.ch3.casatomic.array;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * <p>
 *
 * 原子类型的数组操作类
 * 使用
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-28
 */
public class AtomicArrayMain {

    static int[] array=new int[]{1,2,3,4,5};
    //注意内部使用了数组克隆,所以不会存在引用源数组的问题
    static AtomicIntegerArray atomicIntegerArray=new AtomicIntegerArray(array);

    public static void main(String[] args) {

        for (int i = 0; i < 200; ++i) {
            atomicIntegerArray.set(0,i);
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println(atomicIntegerArray.get(0));

    }


}
