package com.example.mine;


import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Pref extends PreferenceActivity{
	
	protected  void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
}
