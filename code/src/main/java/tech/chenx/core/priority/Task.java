package tech.chenx.core.priority;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/11 16:13
 * @description this is description about this file...
 */
public class Task implements Runnable, Comparable<Task> {

/**
 * An unbounded {@linkplain java.util.concurrent.BlockingQueue blocking queue} that uses
 * the same ordering rules as class {@link java.util.PriorityQueue} and supplies
 * blocking retrieval operations.  While this queue is logically
 * unbounded, attempted additions may fail due to resource exhaustion
 * (causing {@code OutOfMemoryError}). This class does not permit
 * {@code null} elements.  A priority queue relying on {@linkplain
 * Comparable natural ordering} also does not permit insertion of
 * non-comparable objects (doing so results in
 * {@code ClassCastException}).
 */
    private int priority;

    public int getPriority() {
        return this.priority;
    }

    public Task(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Task anotherTask) {
        return anotherTask.getPriority() - getPriority();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("collect data successfully. the priority of this task is : " + priority);
    }
}
