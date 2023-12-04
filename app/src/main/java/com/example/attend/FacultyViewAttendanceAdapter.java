package com.example.attend;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FacultyViewAttendanceAdapter extends ArrayAdapter<String> {

    private final List<studentattendancedata> students;
    private final LayoutInflater inflater;

    public FacultyViewAttendanceAdapter(Context context, int resource, List<studentattendancedata> students) {
        super(context, resource, studentattendancedata.GetStudentID(students));
        this.students = students;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.faculty_view_attendance_itemlist, null);
        }

        studentattendancedata student = students.get(position);



        TextView rollNoView = convertView.findViewById(R.id.rollNoTextview);
        rollNoView.setText(student.ID);

        TextView nameView = convertView.findViewById(R.id.nameTextview);
        nameView.setText(student.name);


        TextView attendancestats =convertView.findViewById(R.id.attendanceStatusTextview);
        attendancestats.setText(""+student.attendanceStatus);

        if(student.attendanceStatus.equals("present"))
        {
            nameView.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            attendancestats.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
            attendancestats.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
        }
        else {
            nameView.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            attendancestats.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
            attendancestats.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
        }


        return convertView;
    }
}
