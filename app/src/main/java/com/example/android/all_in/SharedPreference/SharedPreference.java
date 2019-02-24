package com.example.android.all_in.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String CUSTOM_PREFERENCE = "com.example.android.all_in.SharedPreference.CUSTOM_PREFERENCE";
    private static final String COUNTER = "number_counter";
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        sharedPreference = context.getSharedPreferences(CUSTOM_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void incrementCounter() {
        int number = getCounter();
        editor.putInt(COUNTER, number + 1);
        editor.apply();
    }

    public int getCounter() {
        return sharedPreference.getInt(COUNTER, 0);
    }

}
