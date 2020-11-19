package com.example.dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tododatabase";
    private static final String TABLE_NAME = "todotable";
    private static final String ID = "id";
    private static final String TASK = "task";

    public DatabaseHandler(){
        super(null,DATABASE_NAME,null,DATABASE_VERSION);
    };
    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TODO_TABLE = "CREATE TABLE "+ TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+TASK + " TEXT"+")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    //updating table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    void addTask(ToDoModel toDoModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK,toDoModel.getTask());

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public List<ToDoModel> getAllToDoTasks(){
        List<ToDoModel> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(sqlQuery,null);

        if(cur.moveToFirst()){
            do{
                ToDoModel task = new ToDoModel();
                task.setId(Integer.parseInt(cur.getString(0)));
                task.setTask(cur.getString(1));
                list.add(task);
            }while (cur.moveToNext());
        }
        return list;
    }

    public int updateTask(ToDoModel toDoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK,toDoModel.getTask());

        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(toDoModel.getId()) });
    }

    public void deleteTask(ToDoModel toDoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " =?", new String[] { String.valueOf(toDoModel.getId()) });
        db.close();
    }

}
