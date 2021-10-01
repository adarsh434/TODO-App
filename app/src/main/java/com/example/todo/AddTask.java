package com.example.todo;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.todo.Models.TodoListModel;
import com.example.todo.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTask extends BottomSheetDialogFragment{

    private EditText editText;
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        DatabaseHandler handler = new DatabaseHandler(getActivity());

        editText = view.findViewById(R.id.taskTitle);
        button = view.findViewById(R.id.addThis);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if(text.length() == 0){
                    Toast.makeText(getContext(), "Type a Task", Toast.LENGTH_SHORT).show();
                }
                else {
                    ((MainActivity)getActivity()).addTodo(text);
                    handler.addTask(new TodoListModel(0, text));
                    Toast.makeText(getContext(), "Task Added", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        });
        return view;
    }

}