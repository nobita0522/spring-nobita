package tech.chenx.core.priority;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/11 15:00
 * @description this is description about this file...
 */
public class PriorityBlockingQueueApplication {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 0, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(), Thread::new, new ThreadPoolExecutor.AbortPolicy());
        executor.execute(new ComparableFutureTask(new Task(100)));
        executor.execute(new ComparableFutureTask(new Task(2)));
        executor.execute(new ComparableFutureTask(new Task(3)));
        executor.execute(new ComparableFutureTask(new Task(4)));
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdownNow));
    }
}
