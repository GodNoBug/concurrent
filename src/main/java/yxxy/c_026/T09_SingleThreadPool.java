package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T09_SingleThreadPool {
    public static void main(String[] args) throws InterruptedException {
        // 线程池只有一个Thread的.保证前后顺序执行的,只有前一个完成才会有第二个执行
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            service.execute(()->{
                System.out.println(j+" "+ Thread.currentThread().getName());
            });
        }
    }
}
