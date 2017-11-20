package com.know.know_tasks.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.know.know_tasks.model.SubTask;
import com.know.know_tasks.model.Task;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class FirebaseManager {

    public static void addSubTask(SubTask subTask) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("subTasks");
        databaseReference.setValue(subTask);
    }

    public static void addTask(Task task) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database
                .getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child(Constants.REF_TASKS).push().setValue(task);
    }

    public static DatabaseReference getTasksReference() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        return database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Constants.REF_TASKS);
    }

}
