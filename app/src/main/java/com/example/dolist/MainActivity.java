package com.example.dolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dolist.Adapter.MyAdapter;
import com.example.dolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText taskTxt;
    private Button addbtn;
    private RecyclerView recyclerView;
    private ImageButton imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        taskTxt = findViewById(R.id.newTaskText);
        imgBtn = findViewById(R.id.getSpeechId);
        addbtn = findViewById(R.id.taskAddBtn);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHandler db = new DatabaseHandler(this);
        List<ToDoModel> tasklist = db.getAllToDoTasks();

        recyclerView.setAdapter(new MyAdapter(this,tasklist));
    }

    public void addItem(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        ToDoModel task = new ToDoModel();
        String txt = taskTxt.getText().toString();
        if(txt.length()>0){
            task.setTask(txt);
            db.addTask(task);
            List<ToDoModel> tasklist = db.getAllToDoTasks();

            Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(new MyAdapter(this,tasklist));
        }
        else{
            Toast.makeText(this,"Minimum one letter needed",Toast.LENGTH_SHORT).show();
        }
        taskTxt.setText("");
    }

    public void getSpeech(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(intent, 10);
        }else{
            Toast.makeText(this,"This device cann't support mic",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data != null){
                   ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    DatabaseHandler db = new DatabaseHandler(this);
                    ToDoModel task = new ToDoModel();
                    task.setTask(result.get(0));
                    db.addTask(task);
                    List<ToDoModel> tasklist = db.getAllToDoTasks();
                    Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(new MyAdapter(this,tasklist));
                }

                break;
        }
    }
}