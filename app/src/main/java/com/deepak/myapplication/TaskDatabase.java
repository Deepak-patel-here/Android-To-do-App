package com.deepak.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskTable.class},version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDAO getTaskDAO();

    //singleton pattern
    private static TaskDatabase taskDatabase;
    public static synchronized TaskDatabase getInstance(Context context){
        if(taskDatabase==null){
            taskDatabase= Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class,"task_db")
                    .fallbackToDestructiveMigration().build();
        }
        return taskDatabase;
    }
}
