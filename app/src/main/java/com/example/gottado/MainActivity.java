package com.example.gottado;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter itemAdapter;
    private ArrayList<Task> items = new ArrayList<>();
    private static final String FILE_NAME = "tasks.dat";

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

        ListView list = findViewById(R.id.list);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        itemAdapter = new TaskAdapter(this, items, this);
        list.setAdapter(itemAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return remove(position);
            }
        });

        // Floating Action Button (FAB) functionality
        FloatingActionButton fabClear = findViewById(R.id.fab_clear);
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTasks();
            }
        });

        // Load saved tasks
        loadTasks();
    }

    private boolean remove(int position) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Task Done! Congratulations!", Toast.LENGTH_LONG).show();
        items.remove(position);
        itemAdapter.notifyDataSetChanged();
        saveTasks();  // Save updated task list
        return true;
    }

    void removeTask(int position) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Task Deleted!", Toast.LENGTH_SHORT).show();
        items.remove(position);
        itemAdapter.notifyDataSetChanged();
        saveTasks();  // Save updated task list
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.edit_text);
        String itemText = input.getText().toString();
        if (!(itemText.isEmpty())) {
            items.add(new Task(itemText, false));
            itemAdapter.notifyDataSetChanged(); // Notify adapter after adding task
            input.setText("");
            saveTasks();  // Save updated task list
        } else {
            Toast.makeText(getApplicationContext(), "Please write a task!", Toast.LENGTH_LONG).show();
        }
    }

    private void clearTasks() {
        Context context = getApplicationContext();
        Toast.makeText(context, "All Tasks Cleared!", Toast.LENGTH_SHORT).show();
        items.clear();
        itemAdapter.notifyDataSetChanged();
        saveTasks();  // Save updated task list
    }

    void saveTasks() {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        try (FileInputStream fis = openFileInput(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            ArrayList<Task> savedItems = (ArrayList<Task>) ois.readObject();
            if (savedItems != null) {
                items.clear();
                items.addAll(savedItems);
            }
            itemAdapter.notifyDataSetChanged(); // Notify adapter after loading tasks
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
