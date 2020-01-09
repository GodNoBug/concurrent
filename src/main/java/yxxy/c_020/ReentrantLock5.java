package yxxy.c_020;

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
 * <p>
 * ReentrantLock还可以指定为公平锁,公平锁类似负载均衡,谁等的时间长,让谁得到那把锁
 */
public class ReentrantLock5 extends Thread {
    private static ReentrantLock lock = new ReentrantLock(true); // 参数为true表示为公平锁.请对比输出结果

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        ReentrantLock5 r = new ReentrantLock5();
        Thread t1 = new Thread(r, "t1");
        Thread t2 = new Thread(r, "t2");
        t1.start();
        t2.start();
    }
}
