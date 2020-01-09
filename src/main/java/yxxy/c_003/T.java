package yxxy.c_003;

public class T {
    private int count = 10;
    //如果synchronized(this)范围是整个方法的话,可以变成以下这种
    public synchronized void m(){ // 等同于在方法的代码执行时要synchronized(this)
            count--;
            System.out.println(Thread.currentThread().getName() + "count =" + count);
    }
}
