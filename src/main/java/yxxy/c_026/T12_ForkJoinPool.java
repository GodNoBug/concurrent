package yxxy.c_026;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

// Fork分叉 Join合并,精灵线程  线程中线程,类似递归
public class T12_ForkJoinPool {
    // 大任务切分成一个个小的Task,还是太大还可以继续分
    // 分完计算后合并成一个结果

    // 一百万个数,进行总和计算
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000; // 每个小任务计算不超过该数字
    static Random r = new Random();


    static {
        // 初始化数组
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        // 第一种方式统计,使用Java8的Stream api
        System.out.println(Arrays.stream(nums).sum());
    }

    // 分而治之 RecursiveAction extends ForkJoinTask<Void> RecursiveAction
    static class AddTask extends RecursiveAction {
        int start, end; // 下标开始和结束

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) { // 分到的任务大小满足小于等于MAX_NUM就计算这一段的总合
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                System.out.println("form:" + start + " to:" + end + " = " + sum);
            } else { // 比MAX_NUM长就切,再分成两个子任务
                int middle = start + (end - start) / 2;
                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }
    static class AddTask2 extends RecursiveTask<Long>{
        int start, end; // 下标开始和结束

        public AddTask2(int start, int end) {
            this.start = start;
            this.end = end;
        }


        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) { // 分到的任务大小满足小于等于MAX_NUM就计算这一段的总合
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                return sum;
            } else { // 比MAX_NUM长就切,再分成两个子任务
                int middle = start + (end - start) / 2;
                AddTask2 subTask1 = new AddTask2(start, middle);
                AddTask2 subTask2 = new AddTask2(middle, end);
                subTask1.fork();
                subTask2.fork();
                return subTask1.join()+subTask2.join();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fip =new ForkJoinPool();
        AddTask task =new AddTask(0,nums.length);
        fip.execute(task);
        AddTask2 task2 =new AddTask2(0,nums.length);
        fip.execute(task2);
        Long join = task2.join();
        System.out.println(join);
        System.in.read();
    }

}
