package cn.ch2.forkjoin;

import java.util.Random;

/**
 * <p>
 *
 *  制作指定长度大小的数组
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-26
 */
public class MakeArray {

    //数组最大长度
    public static int arrayLength=3000000;
    //任务拆分最小单位,拆分到这个单位就不能拆分了,即阈值
    public static int splitMixThreshold=(arrayLength/10);


    public static int[] make(){
        int[] origin=new int[arrayLength];
        Random random=new Random();
        for (int i = 0; i < arrayLength; ++i) {
            origin[i]=random.nextInt(arrayLength*3);
        }
        return origin;
    }


}
