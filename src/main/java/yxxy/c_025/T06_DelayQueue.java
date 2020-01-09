package yxxy.c_025;



import java.util.Random;
import java.util.concurrent.*;

/**
 * 可以做定时执行任务
 */
public class T06_DelayQueue {
    // 阻塞式容器,无界队列,延迟队列
    static BlockingQueue<MyTask> queue =new DelayQueue<>();

    static class MyTask implements Delayed{
        long runningTime;

        public MyTask(long rt) {
            this.runningTime = rt;
        }

        // 以给定的时间单位返回与此对象关联的剩余延迟。
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        //
        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS)<o.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }else if (this.getDelay(TimeUnit.MILLISECONDS)>o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }else {
                return 0;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        long now =System.currentTimeMillis();
        MyTask t1=new MyTask(now+1000);
        MyTask t2=new MyTask(now+2000);
        MyTask t3=new MyTask(now+1500);
        MyTask t4=new MyTask(now+2500);
        MyTask t5=new MyTask(now+500);

        queue.put(t1);
        queue.put(t2);
        queue.put(t3);
        queue.put(t4);
        queue.put(t5);

        System.out.println(queue);

        for (int i = 0; i < 5; i++) {
            System.out.println(queue.take());
        }


    }
}
