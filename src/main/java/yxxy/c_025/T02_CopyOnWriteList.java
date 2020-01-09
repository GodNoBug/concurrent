package yxxy.c_025;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * 写时复制容易 copy on write
 * 多线程环境下,写时效率低,读时效率高
 * 适合读多写少的环境
 *
 * 比如添加元素的时候,把容器复制一份,加一新的,把引用指向新的上面
 * 好处在于不用加锁,读是在新的引用读.读效率非常高,写效率非常低
 */
public class T02_CopyOnWriteList {

    public static void main(String[] args) {
        List<String> list =
                //new ArrayList<>(); //这个并发会出问题
                //new Vector<>(); // 有锁,但是效率低一些
                new CopyOnWriteArrayList<>(); //无锁,但是插入效率非常低
        Random r =new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            Runnable task = () -> {
                for (int i1 = 0; i1 < 1000; i1++) list.add("a"+r.nextInt(10000));
            };
            ths[i] = new Thread(task);
        }

        runAndComputeTime(ths);
        System.out.println(list.size());
    }

    private static void runAndComputeTime(Thread[] ths) {

        long s1 =System.currentTimeMillis();
        Arrays.asList(ths).forEach(Thread::start);
        Arrays.asList(ths).forEach(t->{
            try {
                t.join();     //
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 =System.currentTimeMillis();
        System.out.println(s2-s1);
    }
}
