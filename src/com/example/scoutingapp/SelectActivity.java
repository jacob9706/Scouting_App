package com.example.scoutingapp;

import java.io.File;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;

public class SelectActivity extends Activity {
	
	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
    protected void onCreate(Bundle _savedInstanceState) {
    	super.onCreate(_savedInstanceState);
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select);
        
        File dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Scouting/team_list.sqlite");
        
        if (dbFile.isFile()) {
        	SQLiteDatabase dbTest = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        	if (dbTest.isOpen()) {
        		Log.e("TEST!!!!!!!", "Team Database Opened!!!!!!!!!!!!!!!!!!!!!!!!!!");
        	}
        }
    }

}
