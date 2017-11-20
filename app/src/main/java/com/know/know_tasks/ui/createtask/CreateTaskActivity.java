package com.know.know_tasks.ui.createtask;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.know.know_tasks.R;
import com.know.know_tasks.model.SubTask;
import com.know.know_tasks.model.Task;
import com.know.know_tasks.util.FirebaseManager;
import com.know.know_tasks.util.TaskManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTaskActivity extends AppCompatActivity {

    @BindView(R.id.editTextTaskTitle)
    EditText editTextTaskTitle;

    @BindView(R.id.recyclerViewSubTasks)
    RecyclerView recyclerViewSubTasks;

    private String taskTitle;
    private List<SubTask> subTasks;
    private SubTaskAdapter subTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);

        subTasks = new ArrayList<>();
        subTaskAdapter = new SubTaskAdapter(subTasks);
        recyclerViewSubTasks.setAdapter(subTaskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                addTaskToFirebase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickAddSubTaskFAB(View view) {
        showAddSubTaskDialog();
    }

    private void showAddSubTaskDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final EditText editTextSubTaskTitle = new EditText(this);
        editTextSubTaskTitle.setHint(R.string.subtask_title_hint);
        alertDialog.setTitle(getString(R.string.add_subtask_title));
        alertDialog.setView(editTextSubTaskTitle);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subTaskTitle = editTextSubTaskTitle.getText().toString();
                if (!TextUtils.isEmpty(subTaskTitle)) {
                    alertDialog.dismiss();

                    SubTask subTask = TaskManager.createSubTask(subTaskTitle);
                    subTasks.add(subTask);
                    subTaskAdapter.notifyItemInserted(subTasks.size() - 1);
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void addTaskToFirebase() {
        taskTitle = editTextTaskTitle.getText().toString();
        if (TextUtils.isEmpty(taskTitle)) {
            Toast.makeText(this, "Please add a task title and try again.", Toast.LENGTH_SHORT).show();
        } else if (subTasks.isEmpty()) {
            Toast.makeText(this, "Please add at least 1 sub-task and try again.", Toast.LENGTH_SHORT).show();
        } else {
            Task task = TaskManager.createTask(taskTitle, subTasks);
            FirebaseManager.addTask(task);
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
