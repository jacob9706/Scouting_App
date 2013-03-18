package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBHelperV2 extends SQLiteOpenHelper {

	private static final String 
	DATABASE_LOCATION = Environment.getExternalStorageDirectory().getPath() + "/Scouting/results/results",
	DATABASE_END = ".sqlite";

	public DBHelperV2(Context context, int tabletNumber) {
		super(context, getDBLocation(tabletNumber), null, 2);
	}

	private static String getDBLocation(int tabletNumber) {
		return DATABASE_LOCATION + tabletNumber + DATABASE_END;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConstants.CREATE_TABLE)	;
		Log.d("Created Database", "Created Database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS match_data");

		onCreate(db);
	}

	/**
	 * Insert match data into the database. If the match
	 * exists it will override the old data.
	 * @param data
	 */
	public void insert(DataV2 data) {
		ContentValues values = new ContentValues();
		values.put("team_number", data.teamNumber);
		values.put("match_number", data.matchNumber);

		values.put("auto_score_top", data.autonomousScoreTop);
		values.put("auto_score_middle_left", data.autonomousScoreMiddleLeft);
		values.put("auto_score_middle_right", data.autonomousScoreMiddleRight);
		values.put("auto_score_low", data.autonomousScoreLow);
		values.put("auto_score_misses", data.autonomousMisses);

		values.put("teleop_score_top", data.teleopScoreTop);
		values.put("teleop_score_middle_left", data.teleopScoreMiddleLeft);
		values.put("teleop_score_middle_right", data.teleopScoreMiddleRight);
		values.put("teleop_score_low", data.teleopScoreLow);
		values.put("teleop_score_misses", data.teleopMisses);

		values.put("climb", data.climb);
		values.put("push", data.push);
		values.put("shoot_fivepointer", data.fivePointScore);

		values.put("stats_pickup_method", data.pickupMethod);
		values.put("stats_pickup_speed", data.pickupSpeed);
		values.put("stats_penalties", data.penalties);

		values.put("defence", data.defence);
		values.put("notes", data.notes);

		// TODO: Find cause to Null Pointer Exception on this line.
		SQLiteDatabase db = getWritableDatabase();
		
		if (getMatchData(new QueueItem(data.matchNumber, data.teamNumber, "")) == null)
			db.insert("match_data","team_number", values);
		else
			db.update("match_data", values, "team_number = ? AND match_number = ?", 
					new String[] {Integer.toString(data.teamNumber), Integer.toString(data.matchNumber)});
		db.close();
	}

	/**
	 * Retrieve the data for the match
	 * @param queueItem The match to retrieve from
	 * @return The match data
	 */
	public DataV2 getMatchData(QueueItem queueItem) {
		Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " +
				"match_data WHERE team_number = ? AND match_number = ?",
				new String[] {Integer.toString(queueItem.teamNumber),
				Integer.toString(queueItem.matchNumber)});
		
		if (cursor == null || cursor.getCount() <= 0)
			return null;
		
		cursor.moveToFirst();
		
		DataV2 data = new DataV2(queueItem);
		
		data.autonomousScoreTop = cursor.getInt(cursor.getColumnIndex("auto_score_top"));
		data.autonomousScoreMiddleLeft = cursor.getInt(cursor.getColumnIndex("auto_score_middle_left"));
		data.autonomousScoreMiddleRight = cursor.getInt(cursor.getColumnIndex("auto_score_middle_right"));
		data.autonomousScoreLow = cursor.getInt(cursor.getColumnIndex("auto_score_low"));
		data.autonomousMisses = cursor.getInt(cursor.getColumnIndex("auto_score_misses"));
		
		data.teleopScoreTop = cursor.getInt(cursor.getColumnIndex("teleop_score_top"));
		data.teleopScoreMiddleLeft = cursor.getInt(cursor.getColumnIndex("teleop_score_middle_left"));
		data.teleopScoreMiddleRight = cursor.getInt(cursor.getColumnIndex("teleop_score_middle_right"));
		data.teleopScoreLow = cursor.getInt(cursor.getColumnIndex("teleop_score_low"));
		data.teleopMisses = cursor.getInt(cursor.getColumnIndex("teleop_score_misses"));
		
		data.climb = cursor.getString(cursor.getColumnIndex("climb"));
		data.push = cursor.getString(cursor.getColumnIndex("push"));
		data.fivePointScore = cursor.getString(cursor.getColumnIndex("shoot_fivepointer"));
		
		data.pickupMethod = cursor.getString(cursor.getColumnIndex("stats_pickup_method"));
		data.pickupSpeed = cursor.getString(cursor.getColumnIndex("stats_pickup_speed"));
		data.penalties = cursor.getString(cursor.getColumnIndex("stats_penalties"));
		
		data.defence = cursor.getString(cursor.getColumnIndex("defence"));
		data.notes = cursor.getString(cursor.getColumnIndex("notes"));
		
		return data;
	}

	/**
	 * Constants for the database
	 * @author jacob
	 */
	private class DBConstants {
		public static final String
		CREATE_TABLE = "CREATE TABLE match_data(" +
				"team_number INTEGER," +
				"match_number INTEGER," +

				"auto_score_top INTEGER," +
				"auto_score_middle_left INTEGER," +
				"auto_score_middle_right INTEGER," +
				"auto_score_low INTEGER," +
				"auto_score_misses INTEGER," +

				"teleop_score_top INTEGER," +
				"teleop_score_middle_left INTEGER," +
				"teleop_score_middle_right INTEGER," +
				"teleop_score_low INTEGER," +
				"teleop_score_misses INTEGER," +

				"climb TEXT," +
				"push TEXT," +
				"shoot_fivepointer TEXT," +

				"stats_pickup_method TEXT," +
				"stats_pickup_speed TEXT," +
				"stats_penalties TEXT," +

				"defence TEXT," +
				"notes TEXT" +
				")";
	}

}
