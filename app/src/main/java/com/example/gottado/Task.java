package com.example.gottado;

import java.io.Serializable;

public class Task implements Serializable {
    private String text;
    private boolean done;
    private long addedTimestamp;
    private Long completedTimestamp;

    public Task(String text, boolean done) {
        this.text = text;
        this.done = done;
        this.addedTimestamp = System.currentTimeMillis(); // Track creation time
        this.completedTimestamp = done ? System.currentTimeMillis() : null; // Track completion time
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        this.completedTimestamp = done ? System.currentTimeMillis() : null;
    }

    public long getAddedTimestamp() {
        return addedTimestamp;
    }

    public Long getCompletedTimestamp() {
        return completedTimestamp;
    }
}
