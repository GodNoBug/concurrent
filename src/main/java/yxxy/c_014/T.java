package yxxy.c_014;

import java.util.ArrayList;

/**
 * 对比上一个程序,可以使用synchronized解决,synchronized可以保证可见性和原子性,但volatile只能保证可见性
 */
public class T {
    /*volatile*/ int count = 0;
    synchronized void m(){
        for (int i = 0; i < 1000; i++)  count++;
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
