package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String 
	DATABASE_NAME = "FRC 2013",
	TABLE_DATA = "data";
	
	private static DatabaseHelper mInstance = null;
	
	public static DatabaseHelper getInstance(Context ctx) {
	      
	    if (mInstance == null) {
	      mInstance = new DatabaseHelper(ctx.getApplicationContext());
	    }
	    return mInstance;
	  }

	public DatabaseHelper(Context _context) {
		super(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		String createTableTeams = 
				"CREATE TABLE IF NOT EXISTS " + TABLE_DATA + "(" +
						"team_number INT(4) PRIMARY KEY, " +
						"team_name varchar(30) " +
						");";
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

		onCreate(_db);
	}
	
	// TODO: Add CRUD operations
	public boolean addEntry(MatchData _data) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		return true;
	}

}
