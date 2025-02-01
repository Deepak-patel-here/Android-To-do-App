package com.deepak.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<TaskTable>> allTasks;
    public MutableLiveData<Boolean> isTaskListEmpty = new MutableLiveData<>(true);

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository=new TaskRepository(application);
        allTasks = repository.getTasksAll();
        allTasks.observeForever(tasks -> {
            isTaskListEmpty.setValue(tasks == null || tasks.isEmpty());
        });
    }
    //select
    public LiveData<List<TaskTable>> getAllTasks(){
        allTasks= repository.getTasksAll();
        return allTasks;
    }
    //insert
    public void addNewTask(TaskTable taskTable){repository.addTask(taskTable);}
    //delete
    public void delNewTask(TaskTable taskTable){repository.delTask(taskTable);}
    //update
    public void updateTask(TaskTable taskTable){
        repository.updTask(taskTable);
    }

}
