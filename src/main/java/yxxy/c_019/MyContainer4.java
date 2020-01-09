package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题(淘宝?)
 * 实现一个容器,提供两个方法,add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数达到5个时,线程2给出提示并结束.
 * <p>
 *    使用Latch替代 wait notify来进行通知
 * 好处是通信方式简单,同时也可以指定等待时间,
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定,当count的值为0时当前线程继续运行
 * 当不涉及同步,只是涉及线程通信的时候,用synchronized * wait/notify就显得太重了
 * 应该考虑countdownLatch/cyclicBarrier/semaphore
 */

// 以下为错误示范
public class MyContainer4 {
    // 添加volatile使t2能够得到通知
    volatile List<Object> list = new ArrayList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer4 c = new MyContainer4();
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
                lock.notify();       // t2被唤醒后通知t1继续执行
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
                        lock.notify(); // 调用被锁定对象的notify方法,启动在这个对象上等待的线程
                        // 即使去唤醒了t2线程,可是t1线程仍占有这把锁,t2被唤醒需要重新获得这把锁
                        // 只能等到t1循环结束后,才能继续
                        try {
                            lock.wait(); // 调用被锁定对象的notify方法,释放锁,等待其他线程唤醒,让t2有机会执行
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println("t1 关闭");
            }
        }, "t1").start();
    }
}
