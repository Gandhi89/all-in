package com.example.android.all_in.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "student's_table")
public class Student {

    @PrimaryKey
    @NonNull
    String studentId;
    String studentName;
    String course;
    String instructor;

    public Student(String studentId, String studentName, String course, String instructor) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.course = course;
        this.instructor = instructor;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourse() {
        return course;
    }

    public String getInstructor() {
        return instructor;
    }
}
