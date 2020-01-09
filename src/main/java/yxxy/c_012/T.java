package yxxy.c_012;

import java.util.concurrent.TimeUnit;

/**
 * volatile 关键字,使一个变量在多个线程间可见
 * A,B线程都用到一个变量,Java默认是A线程中保留一份copy,这样如果B线程修改了变量,则A线程未必知道
 * 使用volatile关键字,会让所有线程都会读到变量的修改值
 *
 * 在下面代码中,running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候,会把running值从内存中读取到t1线程的工作区,在运行过中直接使用这个copy,不会每次都去
 * 读取堆内存,这样,当主线程修改running的值后,t1线程感知不到,所以不会停止运行
 *
 * 使用 volatile,将会强制所有线程都去堆内存中读取running值
 *
 * volatile 并不能保证多个线程共同修改running变量时多带来的不一致问题,也就是说volatile不能替代synchronized
 *
 * synchronized即保证可见性又保证原子性
 */
public class T {
    /*volatile*/ boolean running = true; // 对比一下有无volatile的情况下整个程序运行结果的区别
    void m(){
        System.out.println("m start");
        while (running){
            // 线程运行.CPU会把running值读到缓冲区中里
            // 读过来一直在循环,由于CPU一直忙(在while里),
            // 没空读主存里的内容刷新到各自线程的缓存,而一直读缓存中的内容
            // 于是另外线程改了running没用了
            try {
                TimeUnit.SECONDS.sleep(10);
                // CPU有空去读,不过不稳定
                // 这样就可能停止了但是仍是running线程间不可见,去掉就死循环
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // volatile 一旦线程改变running,通知其他线程缓冲区内容过期,重新读一下

        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        T t =new T();
        new Thread(t::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running=false;
    }

}
