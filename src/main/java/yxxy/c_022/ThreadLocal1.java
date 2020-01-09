package yxxy.c_022;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal线程局部变量
 *
 * 该程序volatile不写有可能发生问题也有可能不发生问题
 *
 * 该程序第二个线程修改后,第一个线程就读的到
 * 有时候不想这两个线程如此
 */
public class ThreadLocal1 {
    volatile static Person p=new Person();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.name);
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name="李四";
        }).start();

    }

}
class Person{
    String name ="张三";
}
