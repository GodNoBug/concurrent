package yxxy.c_026;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T10_ScheduledPool {
    public static void main(String[] args) throws InterruptedException {
        // 可复用, 定时任务,且保证固定个数
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

        service.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },0,500,TimeUnit.MILLISECONDS);

        //   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, // 任务
        //                                                  long initialDelay,// 需要延长多少时间开始执行第一个任务
        //                                                  long period,      // 每隔多少时间执行一次
        //                                                  TimeUnit unit);
    }
}
