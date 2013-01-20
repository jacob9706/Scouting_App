package utils;

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
	TABLE_TEAMS = "teams",
	TABLE_MATCHES = "matches",
	TABLE_DATA = "data",
	TABLE_DATA_TYPES = "data_types";

	public DatabaseHelper(Context _context) {
		super(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		String createTableTeams = 
				"CREATE TABLE IF NOT EXISTS " + TABLE_TEAMS + "(" +
						"team_number INT(4) PRIMARY KEY, " +
						"team_name varchar(30) " +
						");";
		String createTableDataTypes = 
				"CREATE TABLE IF NOT EXISTS " + TABLE_DATA_TYPES + "(" +
						"id INTEGER PRIMARY KEY, " +
						"name varchar(30) NOT NULL, " +
						"type varchar(30) NOT NULL " +
						");";
		String createTableMatches = 
				"CREATE TABLE IF NOT EXISTS " + TABLE_MATCHES + "(" +
						"id INTEGER PRIMARY KEY, " +
						"match_number INT NOT NULL, " +
						"team_number INT(4) NOT NULL " +
						");";
		String createTableData =
				"CREATE TABLE IF NOT EXISTS " + TABLE_DATA + "(" +
						"id INTEGER PRIMARY KEY, " +
						"match_id INTEGER NOT NULL, " +
						"data_type INTEGER NOT NULL, " +
						"data TEXT " +
						");";
		_db.execSQL(createTableTeams);
		_db.execSQL(createTableDataTypes);
		_db.execSQL(createTableMatches);
		_db.execSQL(createTableMatches);
		_db.execSQL(createTableData);

		_db.beginTransaction();
		try {
			String query = "INSERT INTO " + TABLE_DATA_TYPES + " VALUES(NULL, (?), (?));";
			SQLiteStatement statement = _db.compileStatement(query);
			
			for (DataType data : Constants.DATA_TYPES) {
				statement.bindString(1, data.name);
				statement.bindString(2, data.type);
				statement.execute();
			}
			
			_db.setTransactionSuccessful();
		} finally {
			_db.endTransaction();
		}
		
		Log.i("Database Test", "Should of created database and tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_TYPES);
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHES);
		_db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);

		onCreate(_db);
	}
	
	// TODO: Add CRUD operations
	
	public DataType testByReturningADataType(String name) {
		
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    Cursor cursor = db.query(TABLE_DATA_TYPES, new String[] { "name",
	            "type" }, "name" + "=?",
	            new String[] { name }, null, null, null, null);
	    if (cursor != null) {
	        cursor.moveToFirst();
	 
	        DataType data = new DataType(cursor.getString(0), cursor.getString(1));
	        return data;
	    }

	    return null;
	    
	}

}
