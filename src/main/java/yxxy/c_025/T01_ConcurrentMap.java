package yxxy.c_025;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Hashtable 往里加任何数据的时候锁定整个对象
 * ConcurrentHashMap 容器分段,每次往里操作的时候,锁定的是分段后的某一段
 *                   多个线程往里插入数据的时候.插入不同段,可以并发的往里插入,就不需要锁定整个容器对象了
 *                   jdk1.8 cas替代分段锁
 *TreeMap 排序好的那种
 *ConcurrentSkipListMap 高并发并且需要排序,跳表,插入稍微低一些,查快
 */
public class T01_ConcurrentMap {

    public static void main(String[] args) {
        //Map<String, String> map =new ConcurrentHashMap<>();         //670
        Map<String, String> map =new ConcurrentSkipListMap<>();
        //Map<String, String> map = new Hashtable<>();  // 最老的map实现,所有实现方法默认带锁 859
        //Map<String, String> map =new HashMap<>();   // Collections.synchronizedXxx
        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start =System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(()->{
                for (int j = 0; j < 10000; j++) map.put("a"+r.nextInt(100000),"a"+r.nextInt(100000));
                latch.countDown();
            });
        }

        Arrays.asList(ths).forEach(Thread::start);
        try {
            latch.await(); // 主线程等到所有的主线程执行完再执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end =System.currentTimeMillis();
        System.out.println(end-start);
    }
}
