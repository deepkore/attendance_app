package com.example.attend;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeAttendanceListAdapter extends ArrayAdapter<String> {

    // Have to implement our own event listener
    // since onItemClick is not working with Custom Adapter
    public interface onItemClickListener{
        void onItemClick(View view, int position);
    }
    private final List<Student> students;
    private final LayoutInflater inflater;
    private onItemClickListener itemClickListener;
    int selectedCount;

    private Map<String, String> attendanceMap = new HashMap<>();

    private final static  String PRESENT = "present", ABSENT = "absent";


    public TakeAttendanceListAdapter(Context context, int resource, ArrayList<Student> students) {
        super(context, resource, Student.GetStudentNamesFromStudents(students));
        this.students = students;

        // Populate attendenceMap
        for(Student student:students){
            attendanceMap.put(student.ID, PRESENT);
        }

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedCount = 0;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.takeattendancelistitem, null);
        }

        Student student = students.get(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        checkBox.setChecked(true);

        LinearLayout linearLayout = convertView.findViewById(R.id.attendanceListItem);
        TextView rollNoView = convertView.findViewById(R.id.rollNo);
        TextView nameView = convertView.findViewById(R.id.name);

        View finalConvertView = convertView;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(finalConvertView, position);
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Getting the value for the ?attr/primary
                    TypedValue value = new TypedValue();
                    int color = getContext().getTheme().getResources().getColor(R.color.lightGreen);
                    ColorStateList colorList = ColorStateList.valueOf(color);
                    linearLayout.setBackgroundTintList(colorList);
                    selectedCount++;

                    checkBox.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
                    nameView.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.lightGreen)));
                    rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesgreen));
                    // Attendance 
                    attendanceMap.put(student.ID, PRESENT);
                } else {
                    checkBox.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
                    nameView.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.red)));
                    rollNoView.setBackground(getContext().getDrawable(R.drawable.gridlinesred));
                    selectedCount--;

                    // Attendance
                    attendanceMap.put(student.ID, ABSENT);
                }
            }
        });


        nameView.setText(student.name);

        rollNoView.setText(student.ID);

        return convertView;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        itemClickListener = listener;
    }

    public Map<String, String> GetAttendanceMap(){
        return attendanceMap;
    }

}
