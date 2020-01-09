package yxxy.c_019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题(淘宝?)
 * 实现一个容器,提供两个方法,add,size
 * 写两个线程,线程1添加10个元素到容器中,线程2实现监控元素的个数,当个数达到5个时,线程2给出提示并结束
 *
 * 给list添加volatile之后,t2能够接到通知,但是,t2线程的死循环很浪费cpu,如果不用死循环,该怎么做呢?
 */

// 以下为错误示范
public class MyContainer2 {
    // 添加volatile使t2能够得到通知
    volatile List<Object> list =new ArrayList<>();
    public void add(Object o){
        list.add(o);
    }
    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer2 c = new MyContainer2();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add "+i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(()->{
            while (true){
                if (c.size()==5){
                    break;
                }
            }
            System.out.println("t2 结束");
        },"t2").start();
    }
}
