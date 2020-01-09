package yxxy.c_002;

/**
 * synchronized (this) 如果范围是整个方法的话 => synchronized写在方法上
 */
public class T {
    private int count = 10;
    public void m(){
        synchronized (this) {   //锁定T类的 对象自身
            count--;
            System.out.println(Thread.currentThread().getName() + "count =" + count);
        }
    }
}
