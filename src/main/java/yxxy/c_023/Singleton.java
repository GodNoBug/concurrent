package yxxy.c_023;

import java.util.Arrays;

/**
 * 线程安全的单例模式
 * 更好的是采用下面的方式,不用加锁也能实现懒加载
 */
public class Singleton {
    public Singleton() {
        System.out.println("singleton");
    }
    private static class Inner{
        private static Singleton singleton =new Singleton();
    }
    private static Singleton getInstance(){
        return Inner.singleton;
    }

    public static void main(String[] args) {
        Thread[] ths =new Thread[200];
        for (int i = 0; i < ths.length; i++) {
            ths[i] =new Thread(()->{
                Singleton.getInstance();
            });
        }
        Arrays.asList(ths).forEach(o->o.start());
    }
}
