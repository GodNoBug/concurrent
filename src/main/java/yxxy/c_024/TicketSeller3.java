package yxxy.c_024;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票,每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个程序
 *
 * 分析下面程序
 */
public class TicketSeller3 {
    private static final Vector<String> tickets =new Vector<>();
    static {
        for (int i = 0; i < 1000; i++) tickets.add("票编号: "+i);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    synchronized (tickets) { // 每销售一张票需要把整个队列锁定,效率并非那么高
                        if (tickets.size() <= 0) break;
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        System.out.println("销售了--"+tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
