package cn.ch1.threadlocal;

/**
 * <p>
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/4/3
 */
public class ThreadLocalNoSafe {

    static Integer test=0;

    static ThreadLocal<Integer> ThreadLocalA=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    static ThreadLocal<Integer> ThreadLocalB=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            ThreadLocalA.set(Integer.sum(ThreadLocalA.get(),1));
            System.out.println("ThreadLocalA初始值为"+ThreadLocalA.get());
            ThreadLocalB.set(Integer.sum(ThreadLocalB.get(),1));
            System.out.println("ThreadLocalB初始值为"+ThreadLocalB.get());
        }
    }


}
