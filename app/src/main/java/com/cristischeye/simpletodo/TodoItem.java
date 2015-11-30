package com.cristischeye.simpletodo;

/**
 * Created by cristi on 11/29/15.
 */
public class TodoItem {
    private String text;
    private boolean isCompleted;

    public TodoItem(String text, boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void toggleIsCompleted() {
        isCompleted = !isCompleted;
    }

    public String toString() {
        return text;
    }

    public String toFileString() {
        return isCompleted ? "c" + text : "i" + text;
    }
}
