package yxxy.c_026;

import java.util.concurrent.Executor;

public class T01_MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        //new Thread(command).start();// 线程调用
        command.run();//方法同步调用
    }
    // Executor 执行器,接口,没有具体实现,顶层接口
    // ExecutorService 接口,继承Executor,有execute之外还有submit,submit可提交Runnable和Callable类型的
    // Callable = Runnable设计出来是供线程调用的,不过Callable有返回值,可抛出异常
    // Executors 类似Arrays的常用工具类,使用Executors的静态方法可返回ExecutorService类型的线程池

}
