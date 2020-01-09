package yxxy.c_025;



import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class T06_ArrayBlockingQueue {
    // 阻塞式容器,有界队列
    static BlockingQueue<String> queue =new ArrayBlockingQueue<>(10);
    static Random random =new Random();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.put("a"+i);
        }
        // 装满继续往里装
        //queue.put("aaa");    // 满了会阻塞
        //queue.add("aaa");    // 满加报异常
        //queue.offer("aaa");  // 不会报异常,返回布尔值判断是否加成功
        //queue.offer("aaa", 1,TimeUnit.SECONDS); // 隔一段时间之内加不进去,即不往里加了

        System.out.println(queue);
    }
}
