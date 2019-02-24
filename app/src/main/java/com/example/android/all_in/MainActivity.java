package com.example.android.all_in;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.all_in.Adapter.MainActivity_RecyclerView_Adapter;
import com.example.android.all_in.Entity.Student;
import com.example.android.all_in.SharedPreference.SharedPreference;
import com.example.android.all_in.ViewModel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivity_RecyclerView_Adapter.ItemOnClick, MainActivity_RecyclerView_Adapter.ButtonClick, SharedPreferences.OnSharedPreferenceChangeListener {

    ViewModel viewModel;
    RecyclerView recyclerView;
    MainActivity_RecyclerView_Adapter adapter;
    List<Student> studentList = new ArrayList<>();
    SharedPreference sharedPreference;
    SharedPreferences defaultPreference;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mainActivity_recyclerView);

        sharedPreference = new SharedPreference(this);
        defaultPreference = PreferenceManager.getDefaultSharedPreferences(this);
        defaultPreference.registerOnSharedPreferenceChangeListener(this);

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        adapter = new MainActivity_RecyclerView_Adapter(MainActivity.this, studentList, MainActivity.this, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllStudentList().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                adapter.setStudents(students);
            }
        });

        /**
         * this method calls shared preference
         */
        sharedPreferenceCheck();

        /**
         * settings preference checkbox
         */
        settings_pref_checkBoxValue();
        /**
         * settings preference listview
         */
        settings_pref_listViewValue();


    }// end of onCreate()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void sharedPreferenceCheck() {
        int i = sharedPreference.getCounter();
        Log.d(TAG, "onCreate: counter : " + i);
        sharedPreference.incrementCounter();
        int i1 = sharedPreference.getCounter();
        Log.d(TAG, "onCreate: updated counter : " + i1);
    }

    /**
     * method is called when recycler view is clicked
     *
     * @param view
     * @param data
     */
    @Override
    public void itemOnClickListner(View view, String data) {
        Intent intent = new Intent(this, Screen2.class);
        startActivity(intent);
    }

    /**
     * handle click button event on delete button of recycler view
     *
     * @param studentID
     */
    @Override
    public void onButtonClickEvent(String studentID) {
        viewModel.remove(studentID);
    }

    /**
     * this method calls default shared preference - listValue
     */
    private void settings_pref_listViewValue() {
        String s1 = defaultPreference.getString(getString(R.string.listView_key), "");
        Log.d(TAG, "onSharedPreferenceChanged: settings_pref_listView_value: " + s1);
    }

    /**
     * this method calls default shared preference - checkBox
     */
    private void settings_pref_checkBoxValue() {
        boolean b = defaultPreference.getBoolean(getString(R.string.settings_pref_checkBox_key), true);
        Log.d(TAG, "onSharedPreferenceChanged: settings_pref_checkBox_value: " + b);
    }

    /**
     * method is called when default shared preference value changes
     *
     * @param sharedPreferences
     * @param s
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.settings_pref_checkBox_key))) {
            boolean b = sharedPreferences.getBoolean(s, true);
            Log.d(TAG, "onSharedPreferenceChanged: settings_pref_checkBox_value: " + b);
        } else if (s.equals(getString(R.string.listView_key))) {
            String s1 = sharedPreferences.getString(s, "");
            Log.d(TAG, "onSharedPreferenceChanged: settings_pref_listView_value: " + s1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (defaultPreference != null) {
            defaultPreference.unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
