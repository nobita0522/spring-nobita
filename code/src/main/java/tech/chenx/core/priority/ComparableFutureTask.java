package tech.chenx.core.priority;

import java.util.concurrent.FutureTask;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/11 17:42
 * @description this is description about this file...
 */
public class ComparableFutureTask extends FutureTask<Object> implements Comparable<ComparableFutureTask> {

    private Task task;

    public Task getTask() {
        return task;
    }

    public ComparableFutureTask(Task task) {
        super(task, null);
        this.task = task;
    }

    @Override
    public int compareTo(ComparableFutureTask comparableFutureTask) {
        return this.task.compareTo(comparableFutureTask.getTask());
    }
}
