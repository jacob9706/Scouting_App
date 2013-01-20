package utils;

public class Constants {
	public static final int
	SCORE_ARRAY_SIZE = 5,

	SCORE_HIGH = 0,
	SCORE_MIDDLE_LEFT = 1,
	SCORE_MIDDLE_RIGHT = 2,
	SCORE_LOW = 3,
	MISSED_SHOTS = 4;

	public static final boolean
	TELEOP_STATUS = true,
	AUTONOMOUS_STATUS = false;

	public static final int
	DATA_TYPE_SCORE_HIGH = 0,
	DATA_TYPE_SCORE_MIDDLE_LEFT = 1,
	DATA_TYPE_SCORE_MIDDLE_RIGHT = 2,
	DATA_TYPE_SCORE_LOW = 3,
	DATA_TYPE_MISSED_SHOTS = 4;

	public static final DataType[] DATA_TYPES = {
		new DataType("score_high", "int"),
		new DataType("score_middle_left", "int"),
		new DataType("score_middle_left", "int"),
		new DataType("score_low", "int"),
		new DataType("missed_shots", "int")
	};
	
	public static DataType getDataType(int _dataType) {
		return DATA_TYPES[_dataType];
	}
}
