package cn.ch3.casatomic.reference;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <p>
 *
 * 原子操作类
 * CAS引用类型使用
 * 具体应用场景:
 *
 * 产生ABA问题
 * 需要多个类型字段一起原子操作
 *
 * </p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021-04-28
 */
public class ReferenceMain {


    /** 使用对象合并多个字段进行CAS比较更新 **/
    static class mergeField{

        static class user{
            private String name;
            private int age;

            public user(String name, int age) {
                this.name = name;
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            @Override
            public String toString() {
                return "user{" +
                        "name='" + name + '\'' +
                        ", age=" + age +
                        '}';
            }

        }

        static user user=new user("刺客五六七",17);

        static AtomicReference<user> atomicReference=new AtomicReference<>(user);

        public static void main(String[] args) {
            System.out.println("atomicReference初始值:"+atomicReference.get().toString());
            System.out.println("------------------------------------------------------");
            user newUser=new user("猪猪侠",15);
            atomicReference.set(new user("1",1));
            System.out.println("是否修改成功:"+atomicReference.compareAndSet(user,newUser));
            System.out.println("atomicReference修改过的值:"+atomicReference.get().toString());
        }

    }


    /** 使用stamped版本更新解决ABA问题**/
    static class stampedSolve{
        static AtomicStampedReference<String> stringAtomicStampedReference=new AtomicStampedReference<>("张三",0);

        public static void main(String[] args) {
            //旧版本号
            int oldVersion=stringAtomicStampedReference.getStamp();
            System.out.println("第一次修改是否成功:"+stringAtomicStampedReference.compareAndSet("张三","王五",
                    oldVersion,stringAtomicStampedReference.getStamp()+1));
            System.out.println("第一次内容输出:"+stringAtomicStampedReference.getReference());
            System.out.println("第二次修改是否成功:"+stringAtomicStampedReference.compareAndSet("王五","李四",
                    oldVersion,stringAtomicStampedReference.getStamp()+1));
            System.out.println("第二次内容输出:"+stringAtomicStampedReference.getReference());
        }

    }


    /** 使用marked版本更新解决ABA问题**/
    static class markedSolve{
        static AtomicMarkableReference<String> atomicMarkableReference=new AtomicMarkableReference("张三",false);

        public static void main(String[] args) {
            //旧版本号
            Boolean marked=atomicMarkableReference.isMarked();
            System.out.println("第一次修改是否成功:"+atomicMarkableReference.compareAndSet("张三","王五",
                    marked,true));
            System.out.println("第一次内容输出:"+atomicMarkableReference.getReference());
            System.out.println("第二次修改是否成功:"+atomicMarkableReference.compareAndSet("王五","李四",
                    marked,false));
            System.out.println("第二次内容输出:"+atomicMarkableReference.getReference());
        }

    }


}
