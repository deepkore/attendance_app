package com.example.attend;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class StudentViewAttendanceAdapter extends ArrayAdapter<String>{


    private final List<studentattendancedata> students;
    private final LayoutInflater inflater;

    public StudentViewAttendanceAdapter(Context context, int resource, List<studentattendancedata> students) {
        super(context, resource, studentattendancedata.GetStudentID(students));
        this.students = students;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_view_attendance_itemlist, null);
        }

        studentattendancedata student = students.get(position);


        TextView date = convertView.findViewById(R.id.dateTextView);
        date.setText(student.date);

        TextView attendancestatus = convertView.findViewById(R.id.attendancestatusTextView);
        attendancestatus.setText(student.attendanceStatus);


        if (student.attendanceStatus.equals("present"))
        {
            date.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            attendancestatus.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.lightGreen)));
        } else {

            date.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            attendancestatus.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.red)));
        }
        return convertView;
    }
}
