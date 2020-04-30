package com.example.android.newsflash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements
            SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
        }

        @Override
        public void onStop() {
            super.onStop();
            /* Unregister the preference change listener */
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onStart() {
            super.onStart();
            /* Register the preference change listener */
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference preference = findPreference(key);

            if (null != preference && preference instanceof CheckBoxPreference) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                if (key.equals(getString(R.string.settings_all_key)) &&
                        checkBoxPreference.isChecked()) {
                    PreferenceScreen prefScreen = getPreferenceScreen();
                    int count = prefScreen.getPreferenceCount();
                    for (int i = 0; i < count; i++) {
                        PreferenceCategory p = (PreferenceCategory) prefScreen.getPreference(i);
                        for (int j = 0; j < p.getPreferenceCount(); j++) {
                            ((CheckBoxPreference) p.getPreference(j)).setChecked(true);
                        }
                    }
                } else if (!checkBoxPreference.isChecked()) {
                    CheckBoxPreference allP = (CheckBoxPreference) findPreference("all");
                    allP.setChecked(false);
                }
            }
        }
    }
}

