package com.example.gottado;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class TaskDiffCallback extends DiffUtil.Callback {
    private final List<Task> oldTasks;
    private final List<Task> newTasks;

    public TaskDiffCallback(List<Task> oldTasks, List<Task> newTasks) {
        this.oldTasks = oldTasks;
        this.newTasks = newTasks;
    }

    @Override
    public int getOldListSize() {
        return oldTasks.size();
    }

    @Override
    public int getNewListSize() {
        return newTasks.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTasks.get(oldItemPosition).getText().equals(newTasks.get(newItemPosition).getText());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Task oldTask = oldTasks.get(oldItemPosition);
        Task newTask = newTasks.get(newItemPosition);
        return oldTask.isDone() == newTask.isDone();
    }
}