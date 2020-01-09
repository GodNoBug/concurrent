package yxxy.c_021;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 挺重要
 * 面试题,写一个固定容量同步容器,拥有put和get方法,以及getCount方法,
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * <p>
 * 使用wait和notify/notifyAll来实现
 * wait通常和while一起出现
 * notify/notifyAll的区别 notify叫醒的是一个,很可能还是原先的那个线程
 * <p>
 * 使用Lock和Condition来实现
 * 对比两种方式,Condition的方式可以更加精确的指定哪些线程被唤醒
 */
public class MyContainer2<T> {
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10; // 最多10个元素
    private int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();   // 生产者条件
    private Condition consumer = lock.newCondition();   // 消费者条件

    public void put(T t) {
        try {
            lock.lock();
            while (list.size() == MAX) {
                producer.await();       // 生产者线程等待
            }
            list.add(t);
            ++count;
            consumer.signalAll();       // 通知消费者线程进行消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized T get() {
        T t = null;
        try {
            lock.lock();
            while (list.size() == 0) {   // 为什么使用while不用if
                consumer.await();        // 消费者线程等待
            }
            t = list.removeFirst();
            count--;
            producer.signalAll();       //  生产者唤醒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public synchronized int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        MyContainer2<String> c = new MyContainer2<>();
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
