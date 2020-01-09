package yxxy.c_004;

/**
 * synchronized static 方法上 <=>  synchronized (T.class)
 */
public class T {
    private static int count = 10;

    //锁定的是当前类的Class类对象
    public synchronized static void m(){ // 等同于在synchronized(yxxy.c_004.T.class)
            count--;
            System.out.println(Thread.currentThread().getName() + "count =" + count);
    }

    public static void mm(){
        synchronized (T.class){ // 静态方法上不可用this
            count--;
        }
    }
}
