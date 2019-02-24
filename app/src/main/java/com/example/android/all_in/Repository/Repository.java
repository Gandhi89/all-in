package com.example.android.all_in.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.all_in.Dao.StudentDao;
import com.example.android.all_in.DataBase.StudentDB;
import com.example.android.all_in.Entity.Student;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    LiveData<List<Student>> allStudentList;
    StudentDB studentDB;
    StudentDao studentDao;

    public Repository(Application application) {
        studentDB = StudentDB.getINSTANCE(application);
        studentDao = studentDB.studentDao();
        allStudentList = studentDao.getAllStudent();
    }

    /**
     * get All students from Database
     *
     * @return
     */
    public LiveData<List<Student>> getAllStudentList() {
        return allStudentList;
    }

    /**
     * method to insert new student to database
     *
     * @param student
     */
    public void insert(Student student) {
        new insertIntoDataBaseAsyncTask(studentDao).execute(student);
    }

    private class insertIntoDataBaseAsyncTask extends AsyncTask<Student, Void, Void> {

        StudentDao studentDao;

        public insertIntoDataBaseAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(students[0]);
            return null;
        }
    }

    /**
     * method to update existing student of database
     *
     * @param student
     */
    public void update(Student student) {
        new updateExistingStudentDataBaseAsyncTask(studentDao).execute(student);
    }

    private class updateExistingStudentDataBaseAsyncTask extends AsyncTask<Student, Void, Void> {

        StudentDao studentDao;

        public updateExistingStudentDataBaseAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.update(students[0]);
            return null;
        }
    }

    /**
     * method to remove student from database
     *
     * @param student
     */

    public void delete(Student student) {
        new DeleteExistingStudentDataBaseAsyncTask(studentDao).execute(student);
    }

    private class DeleteExistingStudentDataBaseAsyncTask extends AsyncTask<Student, Void, Void> {

        StudentDao studentDao;

        public DeleteExistingStudentDataBaseAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.delete(students[0]);
            return null;
        }
    }

    /**
     * method to retrive all student with same name
     *
     * @param studentName
     * @return
     */
    public List<Student> getAllStudentWithName(String studentName) {
        try {
            return new GetAllStudentWithNameAsyncTask(studentDao).execute(studentName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GetAllStudentWithNameAsyncTask extends AsyncTask<String, Void, List<Student>> {

        StudentDao studentDao;

        public GetAllStudentWithNameAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected List<Student> doInBackground(String... strings) {
            List<Student> studentList = studentDao.getAllStudentWithName(strings[0]);
            return studentList;
        }

    }

    /**
     * method to remove student from database using studentId
     *
     * @param studentID
     */

    public void removeStudent(String studentID) {
        new RemoveStudentAsyncTask(studentDao).execute(studentID);
    }

    private class RemoveStudentAsyncTask extends AsyncTask<String, Void, Void> {
        StudentDao studentDao;

        public RemoveStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            studentDao.removeStudent(strings[0]);
            return null;
        }
    }
}

