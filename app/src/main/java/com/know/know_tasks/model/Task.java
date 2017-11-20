package com.know.know_tasks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.know.know_tasks.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class Task implements Parcelable {

    public String title;

    public String state;

    public List<SubTask> subTasks;


    public Task() {
        this.state = Constants.STATE_PENDING;
        this.subTasks = Collections.emptyList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.state);
        dest.writeList(this.subTasks);
    }

    protected Task(Parcel in) {
        this.title = in.readString();
        this.state = in.readString();
        this.subTasks = new ArrayList<SubTask>();
        in.readList(this.subTasks, SubTask.class.getClassLoader());
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
