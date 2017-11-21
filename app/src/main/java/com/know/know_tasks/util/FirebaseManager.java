package com.know.know_tasks.util;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.know.know_tasks.model.SubTask;
import com.know.know_tasks.model.Task;

import java.util.List;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class FirebaseManager {

    public static void addTask(Task task) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database
                .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child(Constants.REF_TASKS).push().setValue(task);
    }

    public static DatabaseReference getTasksReference() {
        return FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS);
    }

    public static void updateSubTaskState(String taskId, String subTaskId, String state) {
        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS)
                .child(taskId)
                .child(Constants.REF_SUB_TASKS)
                .child(subTaskId)
                .child(Constants.REF_STATE)
                .setValue(state);
    }

    public static void updateSubTaskStartTime(String taskId, String subTaskId, long startTime) {
        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS)
                .child(taskId)
                .child(Constants.REF_SUB_TASKS)
                .child(subTaskId)
                .child(Constants.REF_START_TIME)
                .setValue(startTime);
    }

    public static void updateSubTaskEndTime(String taskId, String subTaskId, long endTime) {
        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS)
                .child(taskId)
                .child(Constants.REF_SUB_TASKS)
                .child(subTaskId)
                .child(Constants.REF_END_TIME)
                .setValue(endTime);
    }

    public static DatabaseReference getSubTaskReference(String taskId, String subTaskId) {
        return FirebaseDatabase.getInstance()
                .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS)
                .child(taskId)
                .child(Constants.REF_SUB_TASKS)
                .child(subTaskId);
    }

    public static void updateTaskStateIfNeeded(String taskId) {
        final DatabaseReference taskReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS)
                .child(taskId);
        taskReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);

                // Calculate number of DONE tasks
                int doneTask = 0;
                List<SubTask> subTaskList = task.subTasks;
                for (SubTask subTask : subTaskList) {
                    String subTaskState = subTask.state;
                    if (TextUtils.equals(subTaskState, Constants.STATE_DONE)) {
                        doneTask++;
                    }
                }

                // Update Task if all SubTask are done
                if (doneTask == subTaskList.size()) {
                    taskReference.child(Constants.REF_STATE).setValue(Constants.STATE_DONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
