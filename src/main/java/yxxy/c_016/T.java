package yxxy.c_016;

import java.util.concurrent.TimeUnit;

/**
 * synchronized优化,同步代码块中的语句越少越好
 * 比较m1和m2
 */
public class T {
    int count = 0;
    synchronized void m1(){
        try {
            TimeUnit.SECONDS.sleep(2); // 模拟执行一段费时查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count++;
        try {
            TimeUnit.SECONDS.sleep(2); // 模拟执行一段费时查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    void m2(){
        try {
            TimeUnit.SECONDS.sleep(2); // 模拟执行一段费时查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 业务逻辑中只有下面的这句需要sync,这时不应该给整个方法上锁
        // 采用细粒度的锁,可以使线程占用时间变短,从而提高效率
        synchronized (this){ // 锁粒度细
            count++;
        }
        try {
            TimeUnit.SECONDS.sleep(2); // 模拟执行一段费时查询
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
