package com.example.android.all_in.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.all_in.Dao.StudentDao;
import com.example.android.all_in.Entity.Student;

@Database(entities = Student.class, version = 3, exportSchema = false)
public abstract class StudentDB extends RoomDatabase {

    public abstract StudentDao studentDao();

    private static volatile StudentDB INSTANCE;

    public static StudentDB getINSTANCE(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, StudentDB.class, "student_DB")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new addStudentsWhileCreatingDataBaseAsyncTask(INSTANCE).execute();
        }
    };

    private static class addStudentsWhileCreatingDataBaseAsyncTask extends AsyncTask<Void, Void, Void> {

        StudentDao studentDao;

        public addStudentsWhileCreatingDataBaseAsyncTask(StudentDB studentDB) {
            studentDao = studentDB.studentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.insert(new Student("1", "Elbert", "Physics", "Gravity"));
            studentDao.insert(new Student("2", "Mark", "Maths", "Tony"));
            studentDao.insert(new Student("3", "John", "Physics", "Thomas"));
            studentDao.insert(new Student("4", "Dennis", "Maths", "Elbert"));
            studentDao.insert(new Student("5", "CatWoman", "Drama", "Tom"));
            return null;
        }
    }

}
