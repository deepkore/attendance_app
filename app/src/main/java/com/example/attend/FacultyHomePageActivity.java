package com.example.attend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;


public class FacultyHomePageActivity extends AppCompatActivity {


    private Toolbar mainToolbar;

    private   String id ,name,Table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home_page);

        // Initialize the DatabaseHelper

        // Find the CardViews for the options
        CardView takeAttendanceCard = findViewById(R.id.TakeAttendanceCard);
        CardView viewAttendanceCard = findViewById(R.id.ViewAttendancecard);
        CardView attendanceStatsCard = findViewById(R.id.attendancestatscard);
        CardView addStudentCard = findViewById(R.id.AddStudentCard);
        CardView updatePasswordCard = findViewById(R.id.updatePasswordCard);

        ImageButton imgbtn = findViewById(R.id.logout);

        Intent intent = getIntent();
        if (intent != null) {
             name = intent.getStringExtra("name");
             id = intent.getStringExtra("id");
             Table = intent.getStringExtra("Table");
        }
        mainToolbar = findViewById(R.id.mainToolbar);

        // Change the app title dynamically
        mainToolbar.setTitle("Hello "+name);
            // Set click listeners for the CardViews
            takeAttendanceCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to TakeAttendanceActivity
                    Intent intent = new Intent(FacultyHomePageActivity.this, TakeAttendanceActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });

            viewAttendanceCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to ViewAttendanceActivity
                    Intent intent = new Intent(FacultyHomePageActivity.this, FacultyViewAttendanceActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
        attendanceStatsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyHomePageActivity.this, FacultyAttendanceStatsActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

            addStudentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to AddStudentActivity
                    Intent intent = new Intent(FacultyHomePageActivity.this, AddStudentActivity.class);
                    startActivity(intent);
                }
            });

            updatePasswordCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to AnalyzeAttendanceActivity
                    Intent intent = new Intent(FacultyHomePageActivity.this, UpdateProfileActivity.class);
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
                                Toast.makeText(FacultyHomePageActivity.this, "Logging Out....", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(FacultyHomePageActivity.this,MainActivity.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                                Toast.makeText(FacultyHomePageActivity.this, "Go on with your work...", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        }
    }




