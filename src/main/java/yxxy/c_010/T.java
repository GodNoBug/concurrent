package yxxy.c_010;

import java.util.concurrent.TimeUnit;

/**
 * 一同步方法可以调用另外一个同步方法,一个线程已经拥有某个对象的锁,再次申请的时候仍然会得到该对象的锁
 * 也就是说synchronized获得的锁是可重入的
 * 这里是继承只能有可能发生的场景,子类调用父类的同步方法
 *
 * 是否能被继承???
 */
public class T {
    synchronized void m() {
        System.out.println("m start "+Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m end "+Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        TT tt = new TT();

        new Thread(tt::m).start();
        new Thread(tt::m).start();
    }
}

class TT extends T {
    @Override
    synchronized void m() {
        System.out.println("child m start "+Thread.currentThread().getName());
        super.m();
        System.out.println("child m end "+Thread.currentThread().getName());
    }
}
