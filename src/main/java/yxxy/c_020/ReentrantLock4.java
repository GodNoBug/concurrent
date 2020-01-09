package yxxy.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 * <p>
 * 使用ReentrantLock可以完成同样的功能
 * 需要注意的是,必须要 手动释放锁!!!
 * 使用sync锁定的话如果遇到异常,jvm会自动释放锁,但是lock必须手动释放锁,因此经常在finally中进行锁的释放
 * <p>
 * 使用ReentrantLock可以进行"尝试锁定" tryLock,这样在无法锁定,或者在时间内无法锁定,线程可以决定是否继续等待
 * <p>
 * 使用ReentrantLock还可以调用lockInterrupt方法,可以对线程intercept方法做出响应
 * 在一个线程等待锁的过程中,可以被打断
 */
public class ReentrantLock4 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                lock.unlock();
            }
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            boolean locked = false;
            try {
                //lock.lock();
                lock.lockInterruptibly(); // 可以对interrupt()响应
                locked = lock.isLocked();

                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                if (locked)
                    lock.unlock();
            }
        }, "t2");

        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt(); // 打断线程2的等待
    }
}
