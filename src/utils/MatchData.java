package utils;

import utils.Constants;

public class MatchData {
	public int[] 
			autonomousScores, 
			teleopScores;
	
	public int teamNumber;
	
	public MatchData(int _teamNumber) {
		this.teamNumber = _teamNumber;
		this.autonomousScores = new int[Constants.SCORE_ARRAY_SIZE];
		this.teleopScores = new int[Constants.SCORE_ARRAY_SIZE];
	}
	
	public MatchData(int _teamNumber, int[] _autonomousScores, int[] _teleopScores) {
		this.teamNumber = _teamNumber;
		this.autonomousScores = _autonomousScores;
		this.teleopScores = _teleopScores;
	}
}
