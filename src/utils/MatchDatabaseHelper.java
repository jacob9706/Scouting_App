package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

public class MatchDatabaseHelper {
	
	private static MatchDatabaseHelper INSTANCE = null;
	
	private static final Hashtable<Integer, MatchDatabaseHelper> existingDatabases = new Hashtable<Integer, MatchDatabaseHelper>();
	
	private static final String 
	DATABASE_LOCATION = Environment.getExternalStorageDirectory().getPath() + "/Scouting/tablet",
	DATABASE_END = ".sqlite";
	
	private SQLiteDatabase db = null;
	
	private MatchDatabaseHelper(int dbNumber) {
		
		if (!databaseExists()) {
			File dbFile = new File(getDBLocation(dbNumber));

			if (dbFile.isFile()) {
				// Create Database File
				db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
				
				// Create Table
				db.rawQuery(DBConstants.CREATE_TABLE, null);
			}
		}
		else {
			db = SQLiteDatabase.openDatabase(getDBLocation(dbNumber), null, SQLiteDatabase.OPEN_READWRITE);
		}
	}
	
	public static MatchDatabaseHelper getInstance(int dbNumber) {
		if (existingDatabases.containsKey(dbNumber)) {
			return existingDatabases.get(dbNumber);
		}
		else {
			existingDatabases.put(dbNumber, new MatchDatabaseHelper(dbNumber));
			return existingDatabases.get(dbNumber);
		}
	}
	
	public void insert(MatchData data) {
		ContentValues values = new ContentValues();
		values.put("team_number", data.queueItem.teamNumber);
		values.put("match_number", data.queueItem.matchNumber);
		
		values.put("auto_score_top", (String) data.getData(Constants.KEY_SCORE_HIGH_AUTO).getData());
		values.put("auto_score_middle_left", (String) data.getData(Constants.KEY_SCORE_MIDDLE_LEFT_AUTO).getData());
		values.put("auto_score_middle_right", (String) data.getData(Constants.KEY_SCORE_MIDDLE_RIGHT_AUTO).getData());
		values.put("auto_score_low", (String) data.getData(Constants.KEY_SCORE_LOW_AUTO).getData());
		values.put("auto_score_misses", (String) data.getData(Constants.KEY_SCORE_MISSES_AUTO).getData());
		
		values.put("teleop_score_top", (String) data.getData(Constants.KEY_SCORE_HIGH_TELEOP).getData());
		values.put("teleop_score_middle_left", (String) data.getData(Constants.KEY_SCORE_MIDDLE_LEFT_TELEOP).getData());
		values.put("teleop_score_middle_right", (String) data.getData(Constants.KEY_SCORE_MIDDLE_RIGHT_TELEOP).getData());
		values.put("teleop_score_low", (String) data.getData(Constants.KEY_SCORE_LOW_TELEOP).getData());
		values.put("teleop_score_misses", (String) data.getData(Constants.KEY_SCORE_MISSES_TELEOP).getData());
		
		values.put("climb", (String) data.getData(Constants.KEY_ROBOT_HANGABILITY).getData());
		
		// TODO: Continue with binding values
	}
	
	private boolean databaseExists() {
//		SQLiteDatabase testdb = null;
//		try {
//			testdb = SQLiteDatabase.openDatabase(DATABASE_LOCATION, null, SQLiteDatabase.OPEN_READONLY);
//		} catch (SQLiteException e) {
//			
//		}
//		
//		if (testdb != null) {
//			testdb.close();
//		}
//		
//		return testdb == null ? false : true;
		File dbFile = new File(DATABASE_LOCATION + 1 + DATABASE_END);
		if (dbFile.isFile()) {
			SQLiteDatabase dbTest = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
			if (dbTest.isOpen()) {
				return true;
			}
		}
		return false;
	}
	
	private String getDBLocation(int dbNumber) {
		return DATABASE_LOCATION + dbNumber + DATABASE_END;
	}
	
	private class DBConstants {
		public static final String
		CREATE_TABLE = "CREATE TABLE match_data(" +
				"team_number NUMERIC," +
				"match_number NUMERIC," +
				
				"auto_score_top NUMERIC," +
				"auto_score_middle_left NUMERIC," +
				"auto_score_middle_right NUMERIC," +
				"auto_score_low NUMERIC," +
				"auto_score_misses NUMERIC," +
				
				"teleop_score_top_tower NUMERIC," +
				"teleop_score_top NUMERIC," +
				"teleop_score_middle_left NUMERIC," +
				"teleop_score_middle_right NUMERIC," +
				"teleop_score_low NUMERIC," +
				"teleop_score_misses NUMERIC," +
				
				"climb TEXT," +
				"push TEXT," +
				"shoot_fivepointer TEXT," +
				
				"stats_pickup_method TEXT," +
				"stats_pickup_speed TEXT" +
				"stats_penalties TEXT," +
				
				"defence TEXT," +
				"notes TEXT" +
				")";
	}
	
}
