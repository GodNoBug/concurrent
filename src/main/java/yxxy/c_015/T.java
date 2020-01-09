package yxxy.c_015;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样的问题的高效的方法,使用AtomXXX类
 * AtomXXX类本身方法都是原子性的,但不能保证多个方法连续调用是原子性的
 */
public class T {
    ///*volatile*/ int count = 0;
    AtomicInteger count =new AtomicInteger(0);
    /*synchronized*/ void m(){
        // if count.get()<1000 这样就不保证原子性了
        for (int i = 0; i < 1000; i++)  count.incrementAndGet(); // count++
    }

    public static void main(String[] args) {
        T t =new T();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m,"t"+i));
        }
        threads.forEach(Thread::start);
        threads.forEach((o)->{
            try {
                o.join(); //
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}
