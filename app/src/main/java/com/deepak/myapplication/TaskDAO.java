package com.deepak.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    public void insert(TaskTable taskTable);

    @Delete
    public void delete(TaskTable taskTable);

    @Update
    public void update(TaskTable taskTable);

    @Query("SELECT * FROM tasks_table")
    public LiveData<List<TaskTable>> getAllTasks();
}
