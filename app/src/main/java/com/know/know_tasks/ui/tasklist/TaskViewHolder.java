package com.know.know_tasks.ui.tasklist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.know.know_tasks.R;
import com.know.know_tasks.model.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewSubTaskTitle)
    TextView textViewSubTaskTitle;

    @BindView(R.id.textViewTimer)
    TextView textViewTimer;

    @BindView(R.id.buttonState)
    Button buttonState;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Task task) {
        textViewSubTaskTitle.setText(task.title);
        textViewTimer.setText(task.subTasks.size() + " SubTasks");
        buttonState.setText(task.state);
    }
}
