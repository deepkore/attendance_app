package com.example.attend;

import static com.example.attend.DatabaseHelper.COLUMN_ID;
import static com.example.attend.DatabaseHelper.COLUMN_NAME;
import static com.example.attend.DatabaseHelper.COLUMN_POSITION;
import static com.example.attend.DatabaseHelper.TABLE_FACULTY;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Search_Faculty extends AppCompatActivity {

    EditText E1,E2;
    Button B;
    TextView T;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_faculty);

        E1 = (EditText) findViewById(R.id.id);
        E2 = (EditText) findViewById(R.id.name);
        DatabaseHelper D = new DatabaseHelper(this);

        B = (Button)findViewById(R.id.BSearch);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = E1.getText().toString();
                String n = E2.getText().toString();
                T = (TextView)findViewById(R.id.show);

                List<String> result = searchFaculty(i, n);
                if (result.isEmpty()) {
                    T.setText("No faculty found with the provided details.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String r : result) {
                        sb.append(r).append("\n");
                    }
                    T.setText(sb.toString());
                }
            }
        });
    }
    public List<String> searchFaculty(String id, String name) {
        List<String> resultList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FACULTY + " WHERE " + COLUMN_ID+ " = ? OR " + COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id, name});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int PostIndex= cursor.getColumnIndex(COLUMN_POSITION);
                if (idIndex != -1 && nameIndex != -1 && PostIndex !=-1) {
                    String facultyId = cursor.getString(idIndex);
                    String facultyName = cursor.getString(nameIndex);
                    String facultyBranch = cursor.getString(PostIndex);


                    String resultString = "ID: " + facultyId +
                            ", Name: " + facultyName +
                            ", Position: " + facultyBranch ;


                    resultList.add(resultString);
                } else {
                    // Handle the situation when columns are not found
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return resultList;
    }
}