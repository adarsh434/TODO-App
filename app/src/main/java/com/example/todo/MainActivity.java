package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.todo.Adapters.TodoListAdapter;
import com.example.todo.Models.TodoListModel;
import com.example.todo.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<TodoListModel> taskList = new ArrayList<>();
    RecyclerView todoTasks;
    FloatingActionButton addTodo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler handler = new DatabaseHandler(this);

        todoTasks = findViewById(R.id.allTodos);
        addTodo = findViewById(R.id.addTodo);

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask sheet = new AddTask();

                sheet.show(getSupportFragmentManager(), sheet.getTag());
            }
        });

        SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT status,task FROM todo", null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                for(cursor.move(0);cursor.moveToNext();cursor.isAfterLast()){
                    taskList.add(new TodoListModel(cursor.getInt(0), cursor.getString(1)));
                }
            }
            cursor.close();
        }


        TodoListAdapter adapter = new TodoListAdapter(taskList, MainActivity.this);
        todoTasks.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        todoTasks.setLayoutManager(linearLayoutManager);

        todoTasks.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

    }

    public void addTodo(String task){
        taskList.add((new TodoListModel(0, task)));

        TodoListAdapter adapter = new TodoListAdapter(taskList, MainActivity.this);
        todoTasks.setAdapter(adapter);
    }

    public void deleteTodo(int pos){
        taskList.remove(pos);

        TodoListAdapter adapter = new TodoListAdapter(taskList, MainActivity.this);
        todoTasks.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setIcon(R.drawable.ic_delete)
                .setMessage("Do u want to exit this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

}