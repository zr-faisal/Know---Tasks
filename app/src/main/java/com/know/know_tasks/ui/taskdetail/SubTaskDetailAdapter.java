package com.know.know_tasks.ui.taskdetail;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.know.know_tasks.R;
import com.know.know_tasks.model.SubTask;
import com.know.know_tasks.util.CommonUtils;
import com.know.know_tasks.util.Constants;
import com.know.know_tasks.util.FirebaseManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class SubTaskDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String taskId;
    private final List<SubTask> subTasks;

    public SubTaskDetailAdapter(String taskId, List<SubTask> subTasks) {
        SubTaskDetailAdapter.taskId = taskId;
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

    static class ViewHolder extends RecyclerView.ViewHolder implements ValueEventListener {

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
            updateUI(subTask);

            // Observe for state changes
            String subTaskId = Integer.toString(getAdapterPosition());
            FirebaseManager.getSubTaskReference(taskId, subTaskId).addValueEventListener(this);
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SubTask subTaskSnapshot = dataSnapshot.getValue(SubTask.class);
            updateUI(subTaskSnapshot);

            if (TextUtils.equals(subTaskSnapshot.state, Constants.STATE_DONE)) {
                FirebaseManager.updateTaskStateIfNeeded(taskId);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

        private void updateUI(final SubTask subTask) {
            switch (subTask.state) {
                case Constants.STATE_RUNNING:
                    textViewTimer.setText(CommonUtils.getDateTimeString(subTask.startTime) + " - "
                            + Constants.RUNNING);
                    buttonState.setText(Constants.STATE_DONE);
                    break;
                case Constants.STATE_DONE:
                    textViewTimer.setText(CommonUtils.getDateTimeString(subTask.startTime) + " - "
                            + CommonUtils.getDateTimeString(subTask.endTime));
                    buttonState.setEnabled(false);
                    break;
                default:    // STATE_PENDING is default
                    textViewTimer.setText(Constants.NOT_STARTED);
                    buttonState.setText(Constants.START);
            }

            buttonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (subTask.state) {
                        case Constants.STATE_RUNNING:
                            FirebaseManager.updateSubTaskEndTime(taskId,
                                    Integer.toString(getAdapterPosition()),
                                    System.currentTimeMillis());
                            FirebaseManager.updateSubTaskState(taskId,
                                    Integer.toString(getAdapterPosition()),
                                    Constants.STATE_DONE);
                            break;
                        case Constants.STATE_DONE:
                            break;
                        default:    // STATE_PENDING is default
                            FirebaseManager.updateSubTaskStartTime(taskId,
                                    Integer.toString(getAdapterPosition()),
                                    System.currentTimeMillis());
                            FirebaseManager.updateSubTaskState(taskId,
                                    Integer.toString(getAdapterPosition()),
                                    Constants.STATE_RUNNING);
                    }
                }
            });
        }
    }

}
