package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题(淘宝?)
 * 实现一个容器,提供两个方法,add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数达到5个时,线程2给出提示并结束.
 * <p>
 * 给list添加volatile之后,t2能够接到通知,但是,t2线程的死循环很浪费cpu,如果不用死循环,该怎么做呢?
 * <p>
 * 这里使用wait和notify做到,wait会释放锁,而notify不会释放锁,sleep不释放锁
 * 需要注意的是,运用这种方法,必须保证t2先执行,也就是受让t2监听才可以
 * <p>
 * 阅读下面的程序.并分析出输出结果
 * 可以读到输出结果并不是size*5时t2退出,而是t1结束时t2才接收到通知而退出
 * 想想这是为什么?
 */

// 以下为错误示范
public class MyContainer3 {


    // 添加volatile使t2能够得到通知
    volatile List<Object> list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer3 c = new MyContainer3();
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();  // 当前线程调用被锁定对象的wait(),当前进入等待状态,同时释放锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1 启动");
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if (c.size() == 5) { // notifyAll()
                        lock.notify(); // 调用被锁定对象的notify方法,启动在这个对象上等待的线程,不能指定线程
                        // 即使去唤醒了t2线程,可是t1线程仍占有这把锁,t2被唤醒需要重新获得这把锁
                        // 只能等到t1循环结束后,才能继续
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t2 关闭");
            }
        }, "t1").start();
    }
}
