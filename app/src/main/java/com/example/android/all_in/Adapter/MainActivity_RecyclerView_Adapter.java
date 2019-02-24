package com.example.android.all_in.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.all_in.Entity.Student;
import com.example.android.all_in.R;

import java.util.List;

public class MainActivity_RecyclerView_Adapter extends RecyclerView.Adapter<MainActivity_RecyclerView_Adapter.ViewHolder> {

    Context context;
    List<Student> studentList;
    ItemOnClick itemOnClick;
    ButtonClick buttonClick;

    private static final String TAG = "MainActivity_RecyclerVi";


    public MainActivity_RecyclerView_Adapter(Context context, List<Student> studentList, ItemOnClick itemOnClick, ButtonClick buttonClick) {
        this.context = context;
        this.studentList = studentList;
        this.itemOnClick = itemOnClick;
        this.buttonClick = buttonClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        if (studentList == null) {
            return 0;
        }
        return studentList.size();
    }

    public void setStudents(List<Student> students) {
        this.studentList = students;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView studentName, studentID, course, InstructorName;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentID = itemView.findViewById(R.id.cardView_studentID);
            studentName = itemView.findViewById(R.id.cardView_studentName);
            course = itemView.findViewById(R.id.cardView_course);
            InstructorName = itemView.findViewById(R.id.cardView_InstructorName);
            delete = itemView.findViewById(R.id.cardView_deleteButton);
            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        public void bind(int i) {
            studentName.setText(studentList.get(i).getStudentName());
            studentID.setText(studentList.get(i).getStudentId());
            course.setText(studentList.get(i).getCourse());
            InstructorName.setText(studentList.get(i).getInstructor());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardView_deleteButton:
                    buttonClick.onButtonClickEvent(studentID.getText().toString());
                    break;

                default:
                    Log.d(TAG, "onClick: clicked");
                    String s_name = studentName.getText().toString();
                    itemOnClick.itemOnClickListner(view, s_name);
                    break;
            }
        }
    }

    public interface ItemOnClick {
        void itemOnClickListner(View view, String data);
    }

    public interface ButtonClick {
        void onButtonClickEvent(String studentID);
    }
}
