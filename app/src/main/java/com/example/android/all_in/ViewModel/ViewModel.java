package com.example.android.all_in.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.all_in.Entity.Student;
import com.example.android.all_in.Repository.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    Repository repository;
    private List<Student> studentsWithSameName;
    public LiveData<List<Student>> allStudentList;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allStudentList = repository.getAllStudentList();
    }

    /**
     * method to retrieve student list from database
     *
     * @return
     */
    public LiveData<List<Student>> getAllStudentList() {
        return allStudentList;
    }

    /**
     * method to insert new student into database
     *
     * @param student
     */
    public void insert(Student student) {
        repository.insert(student);
    }

    /**
     * method to update existing student of database
     *
     * @param student
     */
    public void update(Student student) {
        repository.update(student);
    }

    /**
     * method to delete student from database
     *
     * @param student
     */
    public void delete(Student student) {
        repository.delete(student);
    }

    /**
     * method to remove student from database using studentId
     *
     * @param studentID
     */
    public void remove(String studentID) {
        repository.removeStudent(studentID);
    }

    /**
     * method to get student list with same name
     *
     * @param studentName
     * @return
     */
    public List<Student> getStudentWithSameName(String studentName) {
        studentsWithSameName = repository.getAllStudentWithName(studentName);
        return studentsWithSameName;
    }


}
