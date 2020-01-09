package yxxy.c_022;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 线程局部变量.自己的线程自己用,和当前线程绑定,互相之间不冲突没有影响
 * <p>
 * ThreadLocal 是使用空间换时间,synchronized是使用时间换空间
 * 比如在hibernate中session就存在于ThreadLocal中,避免synchronized的使用
 * <p>
 * 运行下面的程序,理解ThreadLocal
 */
public class ThreadLocal2 {
    //volatile static Person p=new Person2();
    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get()); // 拿到空值
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}


