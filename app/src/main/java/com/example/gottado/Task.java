package com.example.gottado;

import java.io.Serializable;

//hello this is some changes

public class Task implements Serializable {
    private String text;
    private boolean isDone;

    public Task(String text, boolean isDone) {
        this.text = text;
        this.isDone = isDone;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
