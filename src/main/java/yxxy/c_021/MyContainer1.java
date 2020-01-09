package yxxy.c_021;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * TODO 挺重要
 * 面试题,写一个固定容量同步容器,拥有put和get方法,以及getCount方法,
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 * wait通常和while一起出现
 * notify/notifyAll的区别 notify叫醒的是一个,很可能还是原先的那个线程
 */
public class MyContainer1<T> {
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10; // 最多10个元素
    private int count = 0;

    public synchronized void put(T t) {
        while (list.size() == MAX) {
            try {
                this.wait();
                // 由于这边已经判断满了.然后进入等待状态.put线程唤醒了所有线程,获得锁的线程将继续往下执行
                // 若有一个线程获取了锁put满里面的数据,那么此次线程获取锁继续往下执行,就无数据可村
                // 应该使用while,循环回去判断是否大小还是满的
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        ++count;
        this.notifyAll();
    }

    public synchronized T get() {
        T t;
        while (list.size() == 0) {   // 为什么使用while不用if
            try {
                this.wait(); // 由于这边已经判断空了.然后进入等待状态.put线程唤醒了所有线程,获得锁的线程将继续往下执行
                             // 若有一个线程获取了锁get光里面的数据,那么此次线程继续往下执行,就无数据可取
                             // 应该使用while,循环回去判断是否大小还是等于0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = list.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public synchronized int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        MyContainer1<String> c = new MyContainer1<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 启动消费者线程
                for (int j = 0; j < 5; j++) {
                    System.out.println(c.get());
                }
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }
    }
}
