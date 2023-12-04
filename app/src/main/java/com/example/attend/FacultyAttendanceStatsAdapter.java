package com.example.attend;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.List;


public class FacultyAttendanceStatsAdapter extends ArrayAdapter<String> {

    private final List<studentattendancedata> students;
    private final LayoutInflater inflater;

    public FacultyAttendanceStatsAdapter(Context context, int resource, List<studentattendancedata> students) {
        super(context, resource, studentattendancedata.GetStudentID(students));
        this.students = students;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.faculty_attendance_stats_listitem, null);
        }

        studentattendancedata student = students.get(position);


        TextView rollNoView = convertView.findViewById(R.id.rollNoTextview);
        rollNoView.setText(student.ID);

        TextView nameView = convertView.findViewById(R.id.nameTextview);
        nameView.setText(student.name);



        TextView attendedclasses =convertView.findViewById(R.id.attendedTextView);

        attendedclasses.setText(""+student.totalClassesAttended);
        String formattedPercentage = String.format("%.2f", student.attendancePercentage);
        TextView percentage =convertView.findViewById(R.id.percentageTextView);
        percentage.setText(formattedPercentage);
        if(student.attendancePercentage>=75)
        {
            nameView.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            attendedclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            percentage.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
        }
        else {
            nameView.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            attendedclasses.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            percentage.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
        }


        return convertView;
    }
}
