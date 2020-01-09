package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的概念
 * 一堆线程装在某个容器内,等你来运行它
 */
public class T05_ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        // 固定个数为5的线程的线程池,提交到第6个线程,若线程占用,则放入线程池所维护的任务队列中
        // 返回值一定实现了ExecutorService接口,可以 execute不需要返回值 submit支持不需要返回值,也支持需要返回值的
        // 好处,任务执行完,线程空闲,但是不会消失,可重用性强,效率高,并发性比较好
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.submit(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);

        service.shutdown();                         // 正常关闭:任务执行完才关闭
        System.out.println(service.isTerminated()); // 任务是否都执行完了,已经终止
        System.out.println(service.isShutdown());   // 是否关闭,不代表就已经执行完了,在关闭的过程中
        System.out.println(service);                // Shutting down

        TimeUnit.SECONDS.sleep(5);
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

    }
}
