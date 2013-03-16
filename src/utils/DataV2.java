package utils;

public class DataV2 {
	// General Info
	public int
	team_number,
	match_number;
	
	// Autonomous Score Info
	public int
	autonomousScoreTop = 0,
	autonomousScoreMiddleLeft = 0,
	autonomousScoreMiddleRight = 0,
	autonomousScoreLow = 0,
	autonomousMisses;
	
	// Teleop Score Info
	public int
	teleopScoreTop = 0,
	teleopScoreMiddleLeft = 0,
	teleopScoreMiddleRight = 0,
	teleopScoreLow = 0,
	teleopMisses = 0;
	
	public String
	climb = "",
	push = "",
	pickupMethod = "",
	pickupSpeed = "",
	penalties = "",
	speed = "",
	defence = "",
	notes = "";
	
	public DataV2(QueueItem q) {
		this.team_number = q.teamNumber;
		this.match_number = q.matchNumber;
	}
}
