package yxxy.c_008;

import java.util.concurrent.TimeUnit;

//对业务写方法加锁
//对业务读方法不加锁
//容易产生脏读问题
public class Account {
    private String name;
    private double balance;

    public static void main(String[] args) {
        Account account =new Account();
        new Thread(()->account.set("张三",100.0)).start(); // TODO 写
        System.out.println(account.getBalance("张三"));
        try {
            TimeUnit.SECONDS.sleep(2);//1,2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getBalance("张三")); // TODO 读
    }

    public synchronized void set(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }
    //也在方法上加synchronized,锁定的是当前对象
    public synchronized double getBalance(String name){
        return this.balance;
    }

}
