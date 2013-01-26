package utils;

public class QueueItem {

	public static final int
	QUEUE_ITEM_COLOR_UNKNOWN = 0,
	QUEUE_ITEM_COLOR_RED = 1,
	QUEUE_ITEM_COLOR_BLUE = 2;

	public static final String
	QUEUE_ITEM_COLOR_UNKNOWN_STRING = "Unknown",
	QUEUE_ITEM_COLOR_RED_STRING = "Red",
	QUEUE_ITEM_COLOR_BLUE_STRING = "Blue";

	public int matchNumber, teamNumber, colorNumber;
	public String color;

	public QueueItem(int _matchNumber, int _teamNumber, int _color) {
		this.matchNumber = _matchNumber;
		this.teamNumber = _teamNumber;
		this.colorNumber = _color;
		
		setColor(_color);
	}
	
	private void setColor(int _color) {
		if (_color == QUEUE_ITEM_COLOR_RED) {
			this.color = QUEUE_ITEM_COLOR_RED_STRING;
		}
		else if (_color == QUEUE_ITEM_COLOR_BLUE) {
			this.color = QUEUE_ITEM_COLOR_BLUE_STRING;
		}
		else {
			this.color = QUEUE_ITEM_COLOR_UNKNOWN_STRING;
		}
	}
}
