package com.example.android.all_in.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.all_in.Entity.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM `student's_table` WHERE studentName = :studentName")
    List<Student> getAllStudentWithName(String studentName);

    @Query("DELETE from `student's_table` WHERE studentId = :studentID")
    void removeStudent(String studentID);

    @Query("SELECT * FROM `student's_table`")
    LiveData<List<Student>> getAllStudent();

}
