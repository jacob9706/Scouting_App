package utils;

import utils.Constants;

public class MatchData {
	public int[] 
			autonomousScores, 
			teleopScores;
	
	public Data<Integer>[] scoresAutonomous = new Data[5];
	
	public String hangability = "", pushability = "";
	
	public QueueItem queueItem;
	
	public MatchData(int _matchNumber, int _teamNumber) {
		this.queueItem = new QueueItem(_matchNumber, _teamNumber, QueueItem.QUEUE_ITEM_COLOR_UNKNOWN);
		this.autonomousScores = new int[Constants.SCORE_ARRAY_SIZE];
		this.teleopScores = new int[Constants.SCORE_ARRAY_SIZE];
		
		for(int i = 0; i < scoresAutonomous.length; i ++) {
			scoresAutonomous[i] = new Data<Integer>(DataTypes.getDataType(DataTypes.AUTONOMOUS_TYPES[i]), 0);
		}
	}
	
//	public MatchData(int _matchNumber, int _teamNumber) {
//		this.queueItem = new QueueItem(_matchNumber, _teamNumber, QueueItem.QUEUE_ITEM_COLOR_UNKNOWN);
//		this.autonomousScores = new int[Constants.SCORE_ARRAY_SIZE];
//		this.teleopScores = new int[Constants.SCORE_ARRAY_SIZE];
//	}
	
	public MatchData(QueueItem _queueItem) {
		this.queueItem = _queueItem;
		this.autonomousScores = new int[Constants.SCORE_ARRAY_SIZE];
		this.teleopScores = new int[Constants.SCORE_ARRAY_SIZE];
	}
	
	public MatchData(int _matchNumber, int _teamNumber, int _color, int[] _autonomousScores, int[] _teleopScores) {
		this.queueItem = new QueueItem(_matchNumber, _teamNumber, _color);
		this.autonomousScores = _autonomousScores;
		this.teleopScores = _teleopScores;
	}
}
