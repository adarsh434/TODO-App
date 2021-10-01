package com.example.todo.Adapters;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.MainActivity;
import com.example.todo.Models.TodoListModel;
import com.example.todo.R;
import com.example.todo.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.viewHolder>{

    ArrayList<TodoListModel> list;
    Context context;
    DatabaseHandler handler;

    public TodoListAdapter(ArrayList<TodoListModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.handler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.todo_list_sample_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int id = position+1;

        TodoListModel model = list.get(position);

        String title = model.getName().trim();
        holder.status.setChecked(toBoolean(model.getStatus()));
        holder.status.setText(title);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do u want to delete '" + holder.status.getText().toString() + "' task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                  handler.deleteTask(id);
                                  ((MainActivity)context).deleteTodo(id-1);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });

        holder.status.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do u want to delete '" + holder.status.getText().toString() + "' task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                handler.deleteTask(id);
                                ((MainActivity)context).deleteTodo(id-1);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });


        holder.status.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(holder.status.isChecked()) {
                    handler.updateTask(id, 1);
                }
                else {
                    handler.updateTask(id, 0);
                }
            }
        });

    }

    private boolean toBoolean(int n){
        if(n==0){
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public int getItemCount() { return list.size(); }



    public class viewHolder extends RecyclerView.ViewHolder{

        CheckBox status;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.checkBox);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
