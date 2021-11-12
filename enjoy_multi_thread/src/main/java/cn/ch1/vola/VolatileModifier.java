package cn.ch1.vola;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/3
 */
public class VolatileModifier {

    private static volatile String student="小狗";


    public static void main(String[] args) {

        new Thread(()->student="小猪").start();
        for (int i = 0; i < 10; i++) {
            new Thread(()-> System.out.println(student)).start();
        }

    }


}
