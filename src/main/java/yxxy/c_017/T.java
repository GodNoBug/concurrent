package yxxy.c_017;

import java.util.concurrent.TimeUnit;

/**
 * 锁定某对象o.如果o的属性发生改变,不影响锁的使用.
 * 但是如果o变成另外一个对象,则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外的对象
 *
 */
public class T {
    Object o =new Object();
    void m(){
        synchronized (o){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        T t =new T();
        // 启动第一个线程,TODO正常情况下while死循环t2是执行不了的
        new Thread(t::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 创建第二个线程
        Thread t2 = new Thread(t::m, "t2");
        t.o=new Object();
        t2.start();
    }
}
