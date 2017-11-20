package com.know.know_tasks.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zr.faisal on 11/20/17.
 */

public class SubTask implements Parcelable {

    public String title;

    public String state;

    public long startTime;

    public long endTime;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.state);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
    }

    public SubTask() {
    }

    protected SubTask(Parcel in) {
        this.title = in.readString();
        this.state = in.readString();
        this.startTime = in.readLong();
        this.endTime = in.readLong();
    }

    public static final Parcelable.Creator<SubTask> CREATOR = new Parcelable.Creator<SubTask>() {
        @Override
        public SubTask createFromParcel(Parcel source) {
            return new SubTask(source);
        }

        @Override
        public SubTask[] newArray(int size) {
            return new SubTask[size];
        }
    };
}
