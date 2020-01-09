package yxxy.c_025;


import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class T05_LinkedBlockingQueue {
    // 阻塞式容器,无界队列
    static BlockingQueue<String> queue =new LinkedBlockingQueue<>();
    static Random random =new Random();
    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                try {
                    queue.put("a"+i);   // 如果满了就会等待
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for(;;){
                    try {
                        System.out.println(Thread.currentThread().getName()+"take-"+queue.take()); // 如果空了会等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c"+i).start();
        }
    }
}
