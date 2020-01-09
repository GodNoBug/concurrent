package yxxy.c_026;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T08_CachedThreadPool {
    public static void main(String[] args) throws InterruptedException {
        //有空闲线程继续执行,没有空闲线程,又来任务,启新线程.直到系统支撑的上限
        //线程默认空闲60s就会被销毁.时间可以指定
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);    // [Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
        for (int i = 0; i < 2; i++) {
            service.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);   // [Running, pool size = 2, active threads = 2, queued tasks = 0, completed tasks = 0]
        TimeUnit.SECONDS.sleep(80);
        System.out.println(service);   // [Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 2]
    }
}
