package yxxy.c_025;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;


public class T08_SynchronousQueue { // 容量为0. 必须有消费者等待消费,没有的话put阻塞,add报错
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue =new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        queue.put("aaa");  // 阻塞等待消费者消费,装的任何东西必须直接交给消费者消费,不能往容器中放
        //queue.add("aaa");   // queue full
        System.out.println(queue.size());
    }
}
