package com.know.know_tasks.ui.taskdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.know.know_tasks.R;
import com.know.know_tasks.model.SubTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class SubTaskDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<SubTask> subTasks;

    public SubTaskDetailAdapter(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subtask_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SubTask subTask = subTasks.get(position);
        ((ViewHolder) holder).bind(subTask);
    }

    @Override
    public int getItemCount() {
        return subTasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewSubTaskTitle)
        TextView textViewSubTaskTitle;

        @BindView(R.id.textViewTimer)
        TextView textViewTimer;

        @BindView(R.id.buttonState)
        Button buttonState;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final SubTask subTask) {
            textViewSubTaskTitle.setText(subTask.title);
            textViewTimer.setText("Time: N/A");
            buttonState.setText("Start");
            buttonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonState.setText("Done");
                    textViewTimer.setText("Timer Running...");
                }
            });
        }
    }

}
