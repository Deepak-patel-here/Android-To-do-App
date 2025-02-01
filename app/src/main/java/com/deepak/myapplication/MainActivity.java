package com.deepak.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.myapplication.databinding.ActivityMainBinding;
import com.deepak.myapplication.databinding.AddTaskBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyViewModel vM;
    TaskDatabase taskDatabase;
    ArrayList<TaskTable> arrayList=new ArrayList<>();
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        vM=new ViewModelProvider(this).get(MyViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setMainActivity(this);
        binding.setViewModel(vM);
        binding.setLifecycleOwner(this);

        FloatingActionButton fab = binding.addtaskFAB;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog(v);
            }
        });

        RecyclerView recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        taskDatabase=TaskDatabase.getInstance(this);
        vM.getAllTasks().observe(this, new Observer<List<TaskTable>>() {

            @Override
            public void onChanged(List<TaskTable> taskTables) {
                arrayList.clear();
                for(TaskTable t:taskTables){
                    Log.v("task",t.getTaskName());
                    arrayList.add(t);
                }
                adapter.notifyDataSetChanged();
            }
        });

        adapter=new RecyclerAdapter(arrayList,vM);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TaskTable t=arrayList.get(viewHolder.getAdapterPosition());
                vM.delNewTask(t);
//                arrayList.remove(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

    }
    public void customDialog(View view){
        Dialog dialog=new Dialog(this);
        AddTaskBinding binding1=DataBindingUtil.inflate(getLayoutInflater(),R.layout.add_task,null,false);
        TaskTable taskTable=new TaskTable();
        binding1.setInputTask(taskTable);
        dialog.setContentView(binding1.getRoot());
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,400// Height
        );

        binding1.doneBtnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task=binding1.taskEdtAlert.getText().toString();
                if(!task.trim().isEmpty()){
                    taskTable.setTaskName(task);
                    vM.addNewTask(taskTable);
                }else{
                    Toast.makeText(MainActivity.this, "Please Enter the Task", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}