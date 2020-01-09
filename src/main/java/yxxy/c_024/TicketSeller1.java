package yxxy.c_024;

import java.util.ArrayList;
import java.util.List;

/**
 * 有N张火车票,每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个程序
 *
 * 分析下面程序
 */
public class TicketSeller1 {
    private static List<String> tickets =new ArrayList<>();
    static {
        for (int i = 0; i < 1000; i++) tickets.add("票编号: "+i);
    }

    public static void main(String[] args) {
        // 非原子性.卖重和超卖现象
        // 超卖: 假设卖到只剩一张票了,10个线程同时访问得出tickets.size()>0,继续往下执行,其中一个线程减成功,其他线程就报错或者超卖了
        // 卖重: 由于remove方法不是同步的.第一个人remove第一张票,第二个人可能又会remove一遍,重复卖了
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (tickets.size()>0){
                    System.out.println("销售了--"+tickets.remove(0));
                }
            }).start();
        }
    }
}
