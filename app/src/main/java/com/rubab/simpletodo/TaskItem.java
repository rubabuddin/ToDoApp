package com.rubab.simpletodo;

import java.io.Serializable;

/**
 * Created by rubab.uddin on 9/8/2016.
 */
public class TaskItem{

    private int id;
    private String taskText;
    private int taskPriority;
    private int taskYear;
    private int taskMonth;
    private int taskDay;

    public TaskItem(int id, String taskText, int taskPriority, int taskYear, int taskMonth, int taskDay) {
        this.id = id;
        this.taskText = taskText;
        this.taskPriority = taskPriority;
        this.taskYear = taskYear;
        this.taskMonth = taskMonth;
        this.taskDay = taskDay;

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

    public int getTaskYear(){
        return taskYear;
    }

    public void setTaskYear(int taskYear) {
        this.taskYear = taskYear;
    }

    public int getTaskMonth(){
        return taskMonth;
    }

    public void setTaskMonth(int taskMonth) {
        this.taskMonth = taskMonth;
    }

    public int getTaskDay(){
        return taskDay;
    }

    public void setTaskDay(int taskDay) {
        this.taskDay = taskDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


