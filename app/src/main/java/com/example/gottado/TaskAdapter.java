package com.example.gottado;

import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> tasks;
    private MainActivity mainActivity;

    public TaskAdapter(ArrayList<Task> tasks, MainActivity mainActivity) {
        this.tasks = tasks;
        this.mainActivity = mainActivity;
    }

    public void updateTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textView.setText(task.getText());

        // Set background and text color based on task status
        if (task.isDone()) {
            holder.textView.setBackgroundResource(R.drawable.task_background_done);
            holder.textView.setTextColor(Color.parseColor("#1A496B"));
        } else {
            holder.textView.setBackgroundResource(R.drawable.task_background_not_done);
            holder.textView.setTextColor(Color.parseColor("#2b2d30"));
        }

        GestureDetector gestureDetector = new GestureDetector(mainActivity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                boolean wasNotDone = !task.isDone();
                task.setDone(!task.isDone());
                mainActivity.reorderTask(holder.getAdapterPosition());
                if (task.isDone() && wasNotDone) {
                    Toast.makeText(mainActivity, "Task completed! Well done!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mainActivity.removeTask(holder.getAdapterPosition());
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        holder.textView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.task_text);
        }
    }
}
