package yxxy.c_026;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class T07_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 从1到200000所有的质数
        long start = System.currentTimeMillis();
        List<Integer> results = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        final int cpuCoreNum = 4;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        MyTask t1 =new MyTask(1,80000);
        MyTask t2 =new MyTask(80001,130000);
        MyTask t3 =new MyTask(130001,170000);
        MyTask t4 =new MyTask(170001,200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);
        start =System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end =System.currentTimeMillis();
        System.out.println(end-start);
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        public MyTask(int s, int e) {
            this.startPos = s;
            this.endPos = e;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(startPos, endPos);
        }
    }

    static boolean isPrime(int num) {  // 判断是否是质数
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private static List<Integer> getPrime(int start, int end) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            if (isPrime(i)) results.add(i);
        }
        return results;
    }
}
