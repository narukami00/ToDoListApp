package com.example.gottado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter itemAdapter;
    private ArrayList<Task> items = new ArrayList<>();
    private static final String FILE_NAME = "tasks.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new TaskAdapter(items, this);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setItemAnimator(new SlideOutRightItemAnimator());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> addItem(view));

        FloatingActionButton fabClear = findViewById(R.id.fab_clear);
        fabClear.setOnClickListener(v -> clearTasks());

        loadTasks();
    }

    public void reorderTask(int oldPosition) {
        if (oldPosition == -1 || oldPosition >= items.size()) return;

        Task task = items.get(oldPosition);
        items.remove(oldPosition);

        int newPosition;
        if (task.isDone()) {
            items.add(task); // Add to end
            newPosition = items.size() - 1;
        } else {
            items.add(0, task); // Add to beginning
            newPosition = 0;
        }

        itemAdapter.notifyItemMoved(oldPosition, newPosition);
        itemAdapter.notifyItemChanged(newPosition); // Add this line
        saveTasks();
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString().trim();
        if (!itemText.isEmpty()) {
            Task newTask = new Task(itemText, false);
            items.add(0, newTask);
            itemAdapter.notifyItemInserted(0);
            input.setText("");
            saveTasks();
        } else {
            Toast.makeText(this, "Please write a task!", Toast.LENGTH_LONG).show();
        }
    }

    void removeTask(int position) {
        if (position < 0 || position >= items.size()) return;

        items.remove(position);
        itemAdapter.notifyItemRemoved(position);
        saveTasks();
        Toast.makeText(this, "Task Deleted!", Toast.LENGTH_SHORT).show();
    }

    private void clearTasks() {
        int size = items.size();
        for (int i = size - 1; i >= 0; i--) {
            items.remove(i);
            itemAdapter.notifyItemRemoved(i);
        }
        saveTasks();
        Toast.makeText(this, "All Tasks Cleared!", Toast.LENGTH_SHORT).show();
    }

    private void saveTasks() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving tasks", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTasks() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            items = (ArrayList<Task>) ois.readObject();
            ois.close();
            fis.close();

            // Update the adapter with the loaded tasks
            itemAdapter.updateTasks(items);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No saved tasks found", Toast.LENGTH_SHORT).show();
        }
    }
}
