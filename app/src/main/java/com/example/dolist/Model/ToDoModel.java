package com.example.dolist.Model;

public class ToDoModel {

    int id;
    String task;

    public ToDoModel(){}
    public ToDoModel(int id,String task){
        this.id = id;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
