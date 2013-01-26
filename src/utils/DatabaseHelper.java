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
	TABLE_TEAMS = "teams",
	TABLE_MATCHES = "matches",
	TABLE_DATA = "data",
	TABLE_DATA_TYPES = "data_types";
	
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
			
			for (DataType data : DataTypes.data.values())
			{
				statement.bindString(1, data.getName());
				statement.bindString(2, data.getType());
				long id = statement.executeInsert();
				data.setId(id);
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
	public boolean addEntry(MatchData _data) {
		SQLiteDatabase db = this.getWritableDatabase();
		
//		String query = "INSERT INTO " + TABLE_MATCHES + " VALUES(NULL, (?), (?));";
//		
//		SQLiteStatement statement = db.compileStatement(query);
//		statement.bindDouble(1, _data.queueItem.matchNumber);
//		statement.bindDouble(2, _data.queueItem.teamNumber);
//		statement.execute();
		
		ContentValues insertValues = new ContentValues();
		insertValues.put("match_number", _data.queueItem.matchNumber);
		insertValues.put("team_number", _data.queueItem.teamNumber);
		
		long lastId = db.insert(TABLE_MATCHES, null, insertValues);
		
		if (lastId == -1) {
			return false;
		}
		
		String query = "INSERT INTO " + TABLE_DATA + " VALUES(NULL, (?),(?),(?));";
		SQLiteStatement statement = db.compileStatement(query);
		
		return true;
	}

}
