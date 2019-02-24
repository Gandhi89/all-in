package com.example.android.all_in;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsPref extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_pref);

        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        int count = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                String val = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, val);
            }
        }
    }

    private void setPreferenceSummary(Preference preference, String val) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(val);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        android.support.v7.preference.Preference preference = findPreference(s);
        if (!(preference instanceof CheckBoxPreference)) {
            if (preference != null) {
                String val = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, val);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
