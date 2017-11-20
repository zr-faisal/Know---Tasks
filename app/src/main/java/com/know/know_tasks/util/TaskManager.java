package com.know.know_tasks.util;

import com.know.know_tasks.model.SubTask;
import com.know.know_tasks.model.Task;

import java.util.List;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class TaskManager {

    public static Task createTask(String taskTitle, List<SubTask> subTasks) {
        Task task = new Task();
        task.title = taskTitle;
        task.state = Constants.STATE_PENDING;
        task.subTasks = subTasks;
        return task;
    }

    public static SubTask createSubTask(String subTaskTitle) {
        SubTask subTask = new SubTask();
        subTask.title = subTaskTitle;
        subTask.state = Constants.STATE_PENDING;
        subTask.startTime = System.currentTimeMillis();
        subTask.endTime = 0;
        return subTask;
    }

}
