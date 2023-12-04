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

public class StudentAttendanceStatsAdapter extends ArrayAdapter<String> {
    private final List<studentattendancedata> students;
    private final LayoutInflater inflater;

    public StudentAttendanceStatsAdapter(Context context, int resource, List<studentattendancedata> students) {
        super(context, resource, studentattendancedata.GetStudentID(students));
        this.students = students;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_attendance_stats_listitem, null);
        }

        studentattendancedata student = students.get(position);

        TextView SubjectTextview = convertView.findViewById(R.id.subjectNameTextView);
        SubjectTextview.setText(student.subject);

        TextView attendedclasses = convertView.findViewById(R.id.classesAttendedTextView);
        attendedclasses.setText("" + student.totalClassesAttended);

        TextView totalclasses = convertView.findViewById(R.id.classesTakenTextView);
        totalclasses.setText(""+student.totalclasses);

        String formattedPercentage = String.format("%.2f", student.attendancePercentage);
        TextView percentage = convertView.findViewById(R.id.percentageTextView);
        percentage.setText(formattedPercentage);

        if (student.attendancePercentage >= 75)
        {
            SubjectTextview.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            totalclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            attendedclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            percentage.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.lightGreen)));
        } else {
            SubjectTextview.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            totalclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            attendedclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            percentage.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.red)));
        }
        return convertView;
    }
}
