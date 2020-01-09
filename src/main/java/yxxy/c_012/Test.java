package yxxy.c_012;

import java.util.concurrent.TimeUnit;

public class Test {
    /*volatile*/ int count;

    public /*synchronized*/ void add(){
        count++;
        System.out.println(Thread.currentThread().getName()+" "+count);
    }
    public static void main(String[] args) {
        Test test =new Test();
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.add();
            }
        },"t1").start();
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.add();
            }
        },"t2").start();

    }
}
