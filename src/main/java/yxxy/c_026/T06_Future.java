package yxxy.c_026;

import java.util.concurrent.*;

public class T06_Future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(5);
            return 1000;
        }); // new Callable(){ Integer call();}
        new Thread(task).start();
        System.out.println("进入等待结果");
        System.out.println(task.get()); //阻塞,一直等到任务执行完成

        // 放在线程池中
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return 1;
        });

        System.out.println(f.get());    //阻塞
        System.out.println(f.isDone()); //是否完成

    }
}
