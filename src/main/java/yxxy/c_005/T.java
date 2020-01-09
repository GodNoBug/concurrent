/**
 * 分析一下程序的输出,一般不按次序输出,且不是原子操作
 * TODO 问题分析:
 */
package yxxy.c_005;

public class T implements Runnable {
    private int count = 10;

    //
    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "-   count =" + count);
    }

    public static void main(String[] args) {
        T t = new T();     // new了一个对象,多个线程共同访问该对象
        for (int i = 0; i < 20; i++) {
            new Thread(t, "THREAD" + i).start();
        }
    }
}
