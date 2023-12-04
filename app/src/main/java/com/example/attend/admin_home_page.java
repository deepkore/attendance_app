package com.example.attend;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class admin_home_page extends AppCompatActivity {

    CardView Addfacultycard,AddStudentCard,AssignClasscard,AddClasscard,AddCourseCard;
    private   String id ,name;
    private Toolbar mainToolbar;
    ImageButton imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        Addfacultycard = (CardView)findViewById(R.id.AddFaculty);
        AddStudentCard = (CardView) findViewById(R.id.AddStudent);
        AssignClasscard= (CardView)findViewById(R.id.btnAssignClass);
        AddClasscard = (CardView)findViewById(R.id.btnAddClass);
        AddCourseCard = findViewById(R.id.btnAddCourse);

        imgbtn = findViewById(R.id.logout);
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            id = intent.getStringExtra("id");
        }
        mainToolbar = findViewById(R.id.mainToolbar);

        // Change the app title dynamically
        mainToolbar.setTitle("Hello "+name);

        Addfacultycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(admin_home_page.this, AddFacultyActivity.class);
                startActivity(intent2);
            }
        });
        AssignClasscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(admin_home_page.this, AssignClassActivity.class);
                startActivity(intent1);
            }
        });
        AddStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to AddStudentActivity
                Intent intent = new Intent(admin_home_page.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });


        AddClasscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(admin_home_page.this, AddClassActivity.class);
                startActivity(intent3);
            }
        });
        AddCourseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(admin_home_page.this, AddCourseActivity.class);
                startActivity(in);

            }
        });
        CardView c2 = (CardView) findViewById(R.id.Searchfaculty);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(admin_home_page.this,Search_Faculty.class);
                startActivity(i1);
            }
        });
        CardView c4 = (CardView) findViewById(R.id.SearchStudent);
        c4.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Intent i3 = new Intent(admin_home_page.this, Search_Student.class);
                                      startActivity(i3);
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
                                Toast.makeText(admin_home_page.this, "Logging Out....", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(admin_home_page.this,MainActivity.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                                Toast.makeText(admin_home_page.this, "Go on with your work...", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}