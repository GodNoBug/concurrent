package yxxy.c_011;

import java.util.concurrent.TimeUnit;

/**
 * 程序在执行过中,如果出现异常,默认情况锁会被是否
 * 所以,在并发处理的过程中,有异常要多加小心,不如可能会发生不一致的情况.
 * 比如,一个web app处理过程中,多个Servlet线程共同访问同一个资源,这是如果异常处理不处理.
 * 在第一个线程中抛出异常,其他线程就会进入同步代码区,有可能会访问到异常产生时的数据.(修改一半的数据?)
 * 因此要非常小心的同步业务逻辑中的异常
 */
public class T {
    int count = 0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName()+" start");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName()+" count="+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count==5){  //TODO 如果不释放锁t2不会被执行,t1出了异常.锁就被释放,t2就有机会
                int i =1/0; // 此处抛出异常锁将被释放,可以在这里进行catch.然后让循环继续
            }
        }
    }

    public static void main(String[] args) {
        T t =new T();
        new Thread(t::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t::m,"t2").start();


    }
}
