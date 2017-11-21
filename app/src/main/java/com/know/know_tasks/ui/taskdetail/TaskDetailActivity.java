package com.know.know_tasks.ui.taskdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.know.know_tasks.R;
import com.know.know_tasks.model.Task;
import com.know.know_tasks.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskDetailActivity extends AppCompatActivity {

    @BindView(R.id.textViewTaskTitle)
    TextView textViewTaskTitle;

    @BindView(R.id.recyclerViewSubTasks)
    RecyclerView recyclerViewSubTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String taskId = getIntent().getStringExtra(Constants.EXTRA_TASK_REF);
        Task task = getIntent().getParcelableExtra(Constants.EXTRA_TASK);
        if (task != null && !TextUtils.isEmpty(taskId)) {
            textViewTaskTitle.setText(task.title);
            recyclerViewSubTasks.setAdapter(new SubTaskDetailAdapter(taskId, task.subTasks));
        }
    }

}
