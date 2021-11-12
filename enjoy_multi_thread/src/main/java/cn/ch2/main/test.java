package cn.ch2.main;

import java.util.Random;

/**
 * <p>
 * 单元测试
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-24
 */
public class test {


    public static void main(String[] args)
            throws InterruptedException {
        Random r = new Random();

        Thread.sleep(1);
        if(r.nextInt(100)>50){
            System.out.println("1");
        }else{
            System.out.println("2");
        }
    }

}
