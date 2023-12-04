package com.example.attend;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);


        EditText Treg = findViewById(R.id.reg);
        EditText Tname = findViewById(R.id.name);
        TextView Tshow = findViewById(R.id.show);


        Button B = (Button) findViewById(R.id.BSearch);
        B.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String r = Treg.getText().toString();
                String n = Tname.getText().toString();

                Tshow.setText("Name : "+n.toString()+"\nRegistration : "+r.toString()+"\nYear : 2022"+"\nCourse : MCA"+"\nSection : B");
            }
        });
    }
}