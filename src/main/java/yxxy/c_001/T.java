package yxxy.c_001;

/**
 * synchronized(o)关键字
 * 对某个对象加锁,锁的不是代码块,而是括号内的锁
 */
public class T {
    // int是线程安全的，double和long的写入是不安全的，读取是安全的
    private int count =10;
    private Object o = new Object(); //对象锁记录堆内存的对象中,而非引用

    public void m(){
        // 任何线程要执行的代码,必须先拿到o的锁,不能加锁,则不能执行下面代码.(锁定的不是代码块,而是括号内对象本身)
        synchronized (o){
            count --;
            System.out.println(Thread.currentThread().getName()+"count ="+count);
        }
    }
}
