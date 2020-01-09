package yxxy.c_025;


import java.util.concurrent.*;


public class T07_TransferQueue {


    public static void main(String[] args) throws InterruptedException {
        // 如果有消费者,直接给消费者,不往队列里扔
        // 没有消费者线程的话.调用transfer就会阻塞
        LinkedTransferQueue<String> queue =new LinkedTransferQueue<>();
        /*new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

        queue.transfer("aaa"); //

        //消费者线程 在后面就会阻塞运行不下去
        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
