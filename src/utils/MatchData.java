package utils;

import java.util.Hashtable;

import utils.Constants;

public class MatchData {	
	@SuppressWarnings("rawtypes")
	public Hashtable<String, Data> dataTable = new Hashtable<String, Data>();
	
	public QueueItem queueItem;
	
	public MatchData(QueueItem _queueItem) {
		this.queueItem = _queueItem;
		
		/*===============================================
		 * New way of storing data using Hashtable and 
		 * a class called Data that holds the key in
		 * the table and an specified data type
		 *=============================================*/
		// Autonomous Variables
		dataTable.put(Constants.KEY_SCORE_HIGH_AUTO, new Data<Integer>(Constants.KEY_SCORE_HIGH_AUTO, 0));
		dataTable.put(Constants.KEY_SCORE_MIDDLE_LEFT_AUTO, new Data<Integer>(Constants.KEY_SCORE_MIDDLE_LEFT_AUTO, 0));
		dataTable.put(Constants.KEY_SCORE_MIDDLE_RIGHT_AUTO, new Data<Integer>(Constants.KEY_SCORE_MIDDLE_RIGHT_AUTO, 0));
		dataTable.put(Constants.KEY_SCORE_LOW_AUTO, new Data<Integer>(Constants.KEY_SCORE_LOW_AUTO, 0));
		dataTable.put(Constants.KEY_SCORE_MISSES_AUTO, new Data<Integer>(Constants.KEY_SCORE_MISSES_AUTO, 0));
		
		// Teleop Variables
		dataTable.put(Constants.KEY_SCORE_HIGH_TELEOP, new Data<Integer>(Constants.KEY_SCORE_HIGH_TELEOP, 0));
		dataTable.put(Constants.KEY_SCORE_MIDDLE_LEFT_TELEOP, new Data<Integer>(Constants.KEY_SCORE_MIDDLE_LEFT_TELEOP, 0));
		dataTable.put(Constants.KEY_SCORE_MIDDLE_RIGHT_TELEOP, new Data<Integer>(Constants.KEY_SCORE_MIDDLE_RIGHT_TELEOP, 0));
		dataTable.put(Constants.KEY_SCORE_LOW_TELEOP, new Data<Integer>(Constants.KEY_SCORE_LOW_TELEOP, 0));
		dataTable.put(Constants.KEY_SCORE_MISSES_TELEOP, new Data<Integer>(Constants.KEY_SCORE_MISSES_TELEOP, 0));
		
		// Robot Variables
		dataTable.put(Constants.KEY_ROBOT_HANGABILITY, new Data<String>(Constants.KEY_ROBOT_HANGABILITY, Constants.HANGABILITY_NO_ATTEMPT));
		dataTable.put(Constants.KEY_ROBOT_PUSHABILITY, new Data<String>(Constants.KEY_ROBOT_HANGABILITY, Constants.PUSHABILITY_EASILY_PUSHED));
		
		dataTable.put(Constants.KEY_ROBOT_PICKUP_METHOD, new Data<String>(Constants.KEY_ROBOT_PICKUP_METHOD, Constants.ROBOT_PICKUP_METHOD));
		dataTable.put(Constants.KEY_ROBOT_PICKUP_SPEED, new Data<String>(Constants.KEY_ROBOT_PICKUP_SPEED, Constants.ROBOT_PICKUP_SPEED));
		
		dataTable.put(Constants.KEY_ROBOT_PENALTIES, new Data<String>(Constants.KEY_ROBOT_PENALTIES, Constants.ROBOT_PENALTIES));
		
		dataTable.put(Constants.KEY_ROBOT_SPEED, new Data<String>(Constants.KEY_ROBOT_SPEED, Constants.ROBOT_SPEED));
		
		dataTable.put(Constants.KEY_ROBOT_DEFENCE, new Data<String>(Constants.KEY_ROBOT_DEFENCE, Constants.ROBOT_DEFENCE));
		
		dataTable.put(Constants.KEY_ROBOT_NOTES, new Data<String>(Constants.KEY_ROBOT_NOTES, Constants.ROBOT_NOTES));
	}
	
	/**
	 * 
	 * Get the data object at the key.
	 * @param key The key to try to fetch
	 * @return The Data at that key or null
	 * the key does not exist.
	 */
	@SuppressWarnings("rawtypes")
	public Data getData(String key) {
		if (dataTable.containsKey(key)) {
			return dataTable.get(key);
		}
		
		return null;
	}
}
