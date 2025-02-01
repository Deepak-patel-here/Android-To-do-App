package com.deepak.myapplication;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class TaskTable  extends BaseObservable{
    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    private int taskId;
    @ColumnInfo(name = "task_name")
    private String taskName;
    @ColumnInfo(name = "task_isCompleted")
    private boolean taskCompleted;


    public TaskTable(String taskName, boolean taskCompleted) {
        this.taskName = taskName;
        this.taskCompleted = taskCompleted;
    }

    public TaskTable() {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    @Bindable
    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
        notifyPropertyChanged(BR.taskCompleted);
    }
}
