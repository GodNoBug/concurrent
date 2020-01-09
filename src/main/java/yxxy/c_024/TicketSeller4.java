package yxxy.c_024;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票,每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个程序
 * <p>
 * 分析下面程序
 */
public class TicketSeller4 {
    private static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) tickets.add("票编号: " + i);
    }

    public static void main(String[] args) {
        System.err.println(tickets.size());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    /// 先取再判断,取后没有任何修改的操作
                    String s = tickets.poll(); // 有原子性,取出来,如果没有就为null
                    // 打断了这里,A线程取出数据,还没来得及判断,另外一个线程把队列拿空了,也不会出问题呀
                    if (s == null) break;
                    else System.out.println("销售了--" + s);
                }
            }, "t" + i).start();
        }
        System.err.println(tickets.size());
    }
}
