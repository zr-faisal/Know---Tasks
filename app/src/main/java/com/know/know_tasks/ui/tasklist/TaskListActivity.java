package com.know.know_tasks.ui.tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.know.know_tasks.R;
import com.know.know_tasks.model.Task;
import com.know.know_tasks.ui.createtask.CreateTaskActivity;
import com.know.know_tasks.ui.taskdetail.TaskDetailActivity;
import com.know.know_tasks.util.Constants;
import com.know.know_tasks.util.FirebaseManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerViewTasks)
    RecyclerView recyclerViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);

        attachFirebaseAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_task:
                startActivity(new Intent(this, CreateTaskActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void attachFirebaseAdapter() {
        final Query taskQuery = FirebaseManager.getTasksReference();
        final FirebaseRecyclerOptions<Task> options =
                new FirebaseRecyclerOptions.Builder<Task>()
                        .setQuery(taskQuery, Task.class)
                        .setLifecycleOwner(this)
                        .build();

        final FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_subtask_list, parent, false);
                return new TaskViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(final TaskViewHolder holder, final int position, final Task task) {
                holder.bind(task);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String taskReference = options.getSnapshots()
                                .getSnapshot(holder.getAdapterPosition()).getRef().getKey();
                        Intent taskDetailIntent = new Intent(
                                TaskListActivity.this, TaskDetailActivity.class);
                        taskDetailIntent.putExtra(Constants.EXTRA_TASK_REF, taskReference);
                        taskDetailIntent.putExtra(Constants.EXTRA_TASK, task);
                        startActivity(taskDetailIntent);
                    }
                });
            }
        };

        recyclerViewTasks.setAdapter(firebaseRecyclerAdapter);
    }

}
