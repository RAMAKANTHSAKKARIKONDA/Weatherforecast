package com.example.weatherforecast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import biz.binarysolutions.weatherusa.R;

public class PreferencesActivity extends PreferenceActivity 
	implements OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
    }

	@Override
	protected void onResume() {
		super.onResume();
		
		getPreferenceScreen().
    		getSharedPreferences().
    			registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
        getPreferenceScreen().
    		getSharedPreferences().
    			unregisterOnSharedPreferenceChangeListener(this); 		
	}

	@Override
	public void onSharedPreferenceChanged
		(
				SharedPreferences sharedPreferences, 
				String            key
		) {
		// do nothing
	}	
}
