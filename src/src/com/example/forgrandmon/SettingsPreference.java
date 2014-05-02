package com.example.forgrandmon;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsPreference extends PreferenceActivity { //NOT activity!

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the preference screen defined in /res/xml/preference.xml
        addPreferencesFromResource(R.xml.preferences_settings); //NOT setContentView
    }	

}