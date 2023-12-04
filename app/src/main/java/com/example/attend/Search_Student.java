package com.example.attend;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Search_Student extends AppCompatActivity {

    EditText E1 ;
    Button searchbutton;
    TextView showdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        E1 = (EditText)findViewById(R.id.id);
        showdetails = (TextView)findViewById(R.id.show);
        searchbutton = findViewById(R.id.BSearch);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regno = E1.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getBaseContext());
                Student student= db.getstudentdetails(regno);
                StringBuilder stringBuilder = new StringBuilder();
                if(student!=null) {
                    stringBuilder.append("Reg No: ").append(regno).append("\n");
                    stringBuilder.append("Name: ").append(student.name).append("\n");
                    stringBuilder.append("Stream: ").append(student.stream).append("\n");
                    stringBuilder.append("Semester: ").append(student.sem).append("\n");
                    stringBuilder.append("Section: ").append(student.section).append("\n");
                    showdetails.setText(stringBuilder.toString());
                }
                else
                {
                    showdetails.setText("No Student details found");
                }
            }
        });

        }
    }
