package cn.ch2.forkjoin.sort;

import cn.ch2.forkjoin.MakeArray;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * <p>
 *
 * 使用ForkJoin归并排序
 * 实现两个数组交叉比对升序 归并(合并) 往上递归提交任务
 * 具体:
 * 实现数据量比较大的数组的升序操作
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-26
 */
public class ForkJoinSort {


    static class SortTask extends RecursiveTask<int []>{

        int[] recursionArray;

        public SortTask(int[] recursionArray) {
            this.recursionArray = recursionArray;
        }

        /**
         * 采用历史游走推箱子比较
         * 比较目标跟下一个目标,下一个目标还会取跟上一个目标比较
         * 直到历史目标小于下一个目标就结束比较
         *@Param [array]
         *@return int[]
         */
        public int[] sokobanAsc(int[] array){
            int nextTarget;
            for (int i = 0; i < array.length-1; ++i) {
                //历史指针序列
                int historyPointer=i;
                nextTarget=array[i+1];
                //游走已经比较过的,如果大于目前数值就把历史数据推箱子推给下一位
                while (historyPointer>=0 && nextTarget < array[historyPointer]){
                    //如果之前数值的大于nextTarget这个数值,就把历史大于数据往前面移
                    array[historyPointer+1]=array[historyPointer];
                    --historyPointer;
                }
                //把nextTarget推入正确位置,跟推箱子游戏有些类似
                array[historyPointer+1]=nextTarget;
            }
            return array;
        }


        /**
         * 交叉比较合并
         * 左边数组跟右边数组进行交叉比较大小然后插入结果集
         *@Param [left, right]
         *@return int[]
         */
        public int[] forkCompareMerge(int[] left,int[] right){
            int[] merge=new int[left.length+right.length];
            for (int mergeIndex = 0,leftIndex = 0, rightIndex = 0; mergeIndex < merge.length; ++mergeIndex) {
                if(leftIndex>=left.length){
                    //消除多余比较值,如果左边数组比较完了,循环还是进来了,说明右边数组有多余值,直接把右边的值填入合并数组
                    merge[mergeIndex]=right[rightIndex++];

                }else if(rightIndex>=right.length){
                    //消除多余比较值,如果右边数组比较完了,循环还是进来了,说明左边数组有多余值,直接把左边的值填入合并数组
                    merge[mergeIndex]=left[leftIndex++];

                }else if(left[leftIndex]>right[rightIndex]){
                    //拿左边数组值循环取比较右边数组值,大于,我就复制右边数组的值
                    merge[mergeIndex]=right[rightIndex++];

                }else{
                    //拿左边数组值循环取比较右边数组值,小于,我就复制左边数组的值
                    merge[mergeIndex]=left[leftIndex++];
                }
            }
            return merge;
        }


        @Override
        protected int[] compute() {
            if(recursionArray.length<= MakeArray.splitMixThreshold){
                return sokobanAsc(recursionArray);
            }else{
                int half=recursionArray.length/2;
                SortTask left=new SortTask(Arrays.copyOfRange(recursionArray,0,half));
                SortTask right=new SortTask(Arrays.copyOfRange(recursionArray,half,recursionArray.length));
                invokeAll(left,right);
                return forkCompareMerge(left.join(),right.join());
            }
        }

    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        SortTask sortTask=new SortTask(MakeArray.make());
        long recordTime=System.currentTimeMillis();
        int[] join=forkJoinPool.invoke(sortTask);
        System.out.println("数组升序总用时:"+(System.currentTimeMillis()-recordTime));
        System.out.println(join);
    }

}
