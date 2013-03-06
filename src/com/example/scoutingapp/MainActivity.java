package com.example.scoutingapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.QueueItem;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	List<QueueItem> queueItems = new ArrayList<QueueItem>();
	List<String> listViewList = new ArrayList<String>();

	ListView listView = null;

	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select);

		listView = (ListView) findViewById(R.id.mylist);

		String[] listArray = { "No Entries" };

//		File dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Scouting/team_list.sqlite");
		File dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Scouting/tablet1.sqlite");

		if (dbFile.isFile()) {
			SQLiteDatabase dbTest = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
			if (dbTest.isOpen()) {
				Log.e("TEST!!!!!!!", "Team Database Opened!!!!!!!!!!!!!!!!!!!!!!!!!!");
				Cursor cursor = dbTest.rawQuery("SELECT * FROM teams", null);
				
				if (cursor.moveToFirst()) {
					do {
						queueItems.add(new QueueItem(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
						listViewList.add("Match: " + cursor.getInt(0) + "    Team: " + cursor.getInt(1));
					} while (cursor.moveToNext());
					
					listArray = listViewList.toArray(listArray);
				}

			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, listArray);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ScoutingActivity.class);
				intent.putExtra("match_number", queueItems.get(position).matchNumber);
				intent.putExtra("team_number", queueItems.get(position).teamNumber);
				intent.putExtra("team_color", queueItems.get(position).color);
				startActivity(intent);
			}
		}); 
	}

}
