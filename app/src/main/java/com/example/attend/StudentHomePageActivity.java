package com.example.attend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StudentHomePageActivity extends AppCompatActivity {
    private Toolbar mainToolbar;

    private   String id ,name,Table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        // Initialize the DatabaseHelper

            // Find the CardViews for the options
            CardView AttendanceStatsCard = findViewById(R.id.attendancestats);
            CardView viewAttendanceCard = findViewById(R.id.viewattendance);
            CardView updatepasswordCard= findViewById(R.id.updatepassword);
            ImageButton imgbtn = findViewById(R.id.logout);

            Intent intent = getIntent();
            if (intent != null) {
                name = intent.getStringExtra("name");
                id = intent.getStringExtra("id");
                Table =  intent.getStringExtra("Table");
            }
            mainToolbar = findViewById(R.id.mainToolbar);

            // Change the app title dynamically
            mainToolbar.setTitle("Hello "+name);
            // Set click listeners for the CardViews


            viewAttendanceCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to ViewAttendanceActivity
                    Intent intent = new Intent(StudentHomePageActivity.this, StudentViewAttendanceActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
            AttendanceStatsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudentHomePageActivity.this, StudentAttendanceStatsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });



            updatepasswordCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StudentHomePageActivity.this,UpdateProfileActivity.class);
                    intent.putExtra("Table",Table);
                    startActivity(intent);
                }
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage("Do you want to logout")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                    finish();
                                    Toast.makeText(StudentHomePageActivity.this, "Logging Out....", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(StudentHomePageActivity.this,MainActivity.class);
                                    startActivity(intent1);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                    Toast.makeText(StudentHomePageActivity.this, "Go on with your work...", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }


    }
