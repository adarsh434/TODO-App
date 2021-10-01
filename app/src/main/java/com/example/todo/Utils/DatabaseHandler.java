package com.example.todo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Models.TodoListModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, "todolist.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE todo ( id INTEGER , task VARCHAR(50), status INTEGER)";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS todo");
        onCreate(db);
    }

    public void addTask(TodoListModel model) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor key = db.rawQuery("SELECT MAX(id) FROM todo", null);
        key.moveToFirst();

        values.put("id", key.getInt(0) + 1);
        values.put("task", model.getName());
        values.put("status", model.getStatus());

        db.insert("todo", null, values);
    }

    public void updateTask(int position, int checked){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE todo SET status="+checked+" WHERE id="+position);

    }

    public void deleteTask(int position) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor value = db.rawQuery("SELECT id FROM todo", null);
        value.moveToFirst();

        int target = 1;
        while (target != position) {
            value.moveToNext();
            target++;
        }

        db.execSQL("DELETE FROM todo WHERE id=" + value.getInt(0));
    }
}


