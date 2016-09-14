package com.rubab.simpletodo;

import java.io.Serializable;

/**
 * Created by rubab.uddin on 9/8/2016.
 */
public class TaskItem{

    private int id;
    private String taskText;
    private int taskPriority;
    private String taskDate;

    public TaskItem(int id, String taskText, int taskPriority, String taskDate) {
        super();
        this.id = id;
        this.taskText = taskText;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
    }

    public String getTaskText(){
        return taskText;
    }

    public void setTaskText(String taskText){
        this.taskText = taskText;
    }

    public int getTaskPriority(){
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority){
        this.taskPriority = taskPriority;
    }

    public String getTaskDate(){
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


