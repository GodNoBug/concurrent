package yxxy.c_007;
//TODO 同步方法和非同步方法是否可以同时调用
// 答案:可以,但是非同步方法不会等待同步方法执行完毕继续执行.因为只有同步方法去申请锁
public class T {
    public static void main(String[] args) {
        T t =new T();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.m2();
            }
        }).start();
    }
    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName()+" m1 start....");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" m1 end");
    }
    public void m2(){
        System.out.println(Thread.currentThread().getName()+" m2 start");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(Thread.currentThread().getName()+" m2");
    }
}
