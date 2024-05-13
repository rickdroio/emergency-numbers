package com.rdr.emergency;

import android.app.Activity;
import android.content.SharedPreferences;

public class Usuario {
	
	private Activity myactivity;
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	
	private static final String PREFS_NAME = "EmergencyNumberPrefsFile";
		
	Usuario(Activity myactivity){
		this.myactivity=myactivity;
		
		settings=this.myactivity.getSharedPreferences(PREFS_NAME, 0);
		editor=settings.edit(); 
	}
	
	public void SetUserRating(int rating)
	{	
		//0 = LATER
		//1 = YES
		//2 = NEVER
		
		editor.putInt("RATING", rating);
		editor.commit();
	}	
	
	public int GetUserRating(){
		int s = settings.getInt("RATING", 0);
		return s;
	}	
	
}
