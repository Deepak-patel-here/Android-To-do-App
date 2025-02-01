package com.deepak.myapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deepak.myapplication.databinding.RecycleItemListBinding;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<TaskTable> TaskArrayList;
    private MyViewModel viewModel;

    public RecyclerAdapter(ArrayList<TaskTable> taskArrayList,MyViewModel viewModel) {
        TaskArrayList = taskArrayList;
        this.viewModel=viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleItemListBinding itemListBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recycle_item_list,parent,false);
        return new MyViewHolder(itemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskTable taskTable=TaskArrayList.get(position);
        holder.itemListBinding.setRecycleData(taskTable);
        holder.itemListBinding.checkBox.setChecked(taskTable.isTaskCompleted());
        holder.itemListBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                taskTable.setTaskCompleted(isChecked);
                viewModel.updateTask(taskTable);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(TaskArrayList!=null)return TaskArrayList.size();
        else return 0;
    }
    public void setTaskArrayList(ArrayList<TaskTable> taskArrayList) {
        TaskArrayList = taskArrayList;

        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        RecycleItemListBinding itemListBinding;

        public MyViewHolder(@NonNull RecycleItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.itemListBinding = itemListBinding;
        }
    }


}
