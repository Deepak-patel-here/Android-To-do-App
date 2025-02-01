package com.deepak.myapplication;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDAO taskDAO;
    ExecutorService executorService;
    Handler handler;

    public TaskRepository(Application application) {
        TaskDatabase taskDatabase=TaskDatabase.getInstance(application);
        this.taskDAO=taskDatabase.getTaskDAO();
        executorService= Executors.newSingleThreadExecutor();
        handler=new Handler(Looper.getMainLooper());
    }

    //insert
    public void addTask(TaskTable taskTable){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDAO.insert(taskTable);
            }
        });
    }
    //delete
    public void delTask(TaskTable taskTable){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDAO.delete(taskTable);
            }
        });
    }

    public void updTask(TaskTable taskTable){
        new Thread(()->taskDAO.update(taskTable));
    }

    //select
    public LiveData<List<TaskTable>>getTasksAll(){
        return taskDAO.getAllTasks();
    }

}
