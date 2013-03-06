package utils;

public class QueueItem {

	public static final String
	QUEUE_ITEM_COLOR_UNKNOWN = "0",
	QUEUE_ITEM_COLOR_RED = "1",
	QUEUE_ITEM_COLOR_BLUE = "2";

	public static final String
	QUEUE_ITEM_COLOR_UNKNOWN_STRING = "Unknown",
	QUEUE_ITEM_COLOR_RED_STRING = "red",
	QUEUE_ITEM_COLOR_BLUE_STRING = "blue";

	public int matchNumber, teamNumber;
	public String color;

	public QueueItem(int _matchNumber, int _teamNumber, String _color) {
		this.matchNumber = _matchNumber;
		this.teamNumber = _teamNumber;
		
		setColor(_color);
	}
	
	private void setColor(String _color) {
		if (_color.equals(QUEUE_ITEM_COLOR_RED) || _color.equals(QUEUE_ITEM_COLOR_RED_STRING)) {
			this.color = QUEUE_ITEM_COLOR_RED_STRING;
		}
		else if (_color.equals(QUEUE_ITEM_COLOR_BLUE) || _color.equals(QUEUE_ITEM_COLOR_BLUE_STRING)) {
			this.color = QUEUE_ITEM_COLOR_BLUE_STRING;
		}
		else {
			this.color = QUEUE_ITEM_COLOR_UNKNOWN_STRING;
		}
	}
}
