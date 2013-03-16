package com.example.scoutingapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.QueueItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	String defaultLabel = "No Entries for tablet 0";
	List<QueueItem> queueItems = new ArrayList<QueueItem>();
	List<String> listViewList = new ArrayList<String>();
	Adapter adapter = null;
	
	EditText teamNumber = null;
	ListView listView = null;
	
	int tabletNumber = 0;

	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select);

		adapter = new Adapter(this, 
				android.R.layout.simple_list_item_1, android.R.id.text1, listViewList);
		
		teamNumber = (EditText)findViewById(R.id.edit_text_tablet_number);
		teamNumber.addTextChangedListener(numberListener);
		
		listView = (ListView) findViewById(R.id.mylist);		

		listView.setOnItemClickListener(null);
		listView.setAdapter(adapter);
		
		updateList();
	}
	
	private void updateList() {
		listViewList.clear();
		queueItems.clear();
		adapter.clear();
		listView.invalidateViews();
		
		File dbFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Scouting/tablet" + tabletNumber + ".sqlite");

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
					
					listView.setOnItemClickListener(listListener);
				} else {
					listViewList.add(defaultLabel);
					listView.setOnItemClickListener(null);
					adapter.addAll(listViewList);
				}

			}
		}
		else {
			listViewList.add("No Entries For Tablet " + tabletNumber);
			listView.setOnItemClickListener(null);
		}
		adapter.notifyDataSetChanged();
	}
	
	ListView.OnItemClickListener listListener = new ListView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getApplicationContext(), ScoutingActivity.class);
			intent.putExtra("match_number", queueItems.get(position).matchNumber);
			intent.putExtra("team_number", queueItems.get(position).teamNumber);
			intent.putExtra("team_color", queueItems.get(position).color);
			startActivity(intent);
		}
	};

	TextWatcher numberListener = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			String numStr = teamNumber.getText().toString();
			if (numStr.isEmpty()) {
				tabletNumber = 0;
				updateList();
			}
			else {
				tabletNumber = Integer.parseInt(numStr);
				updateList();
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private class Adapter extends ArrayAdapter<String> {

		public Adapter(Context context, int resource, int textViewResourceId,
				List<String> objects) {
			super(context, resource, textViewResourceId, objects);
			
			setNotifyOnChange(true);
		}
		
	}
	
}
