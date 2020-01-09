package yxxy.c_026;

import org.omg.SendingContext.RunTime;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T11_WorkStealingPool {
    // 每个线程维护自己的任务队列,如果某个线程执行完任务后,会主动的去别的线程的任务队列中偷过来执行,且该线程池产生的是精灵线程
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());//4核

        service.execute(new R(1000)); // 首先执行玩
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        service.execute(new R(2000)); // 第五个线程任务给第一个

        // 由于产生的是精灵线程(守护线程/后台线程),主线程不阻塞的话,看不到输出
        //System.in.read();
    }
    static  class R implements Runnable{
        int time;

        public R(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}
