package com.example.gottado;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    private final MainActivity mainActivity;

    public TaskAdapter(Context context, ArrayList<Task> tasks, MainActivity mainActivity) {
        super(context, 0, tasks);
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.task_text);
        textView.setText(task.getText());

        if (task.isDone()) {
            textView.setBackgroundColor(Color.parseColor("#FFD700"));  // Yellow for done state
            textView.setTextColor(Color.parseColor("#1A496B"));  // Blue for done state
        } else {
            textView.setBackgroundColor(Color.parseColor("#1A496B"));  // Blue for normal state
            textView.setTextColor(Color.parseColor("#FFD700"));  // Yellow for normal state
        }

        textView.setOnClickListener(v -> {
            boolean wasNotDone = !task.isDone();
            task.setDone(!task.isDone());
            if (task.isDone()) {
                textView.setBackgroundColor(Color.parseColor("#FFD700"));
                textView.setTextColor(Color.parseColor("#1A496B"));
                if (wasNotDone) {
                    Toast.makeText(mainActivity, "Task completed! Well done!", Toast.LENGTH_SHORT).show();
                }
            } else {
                textView.setBackgroundColor(Color.parseColor("#1A496B"));
                textView.setTextColor(Color.parseColor("#FFD700"));
            }
            mainActivity.saveTasks();  // Save the updated task list
        });

        // Add GestureDetector for double-tap
        GestureDetector gestureDetector = new GestureDetector(mainActivity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mainActivity.removeTask(position);
                return super.onDoubleTap(e);
            }
        });

        textView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        return convertView;
    }
}
