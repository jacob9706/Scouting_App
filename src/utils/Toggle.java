package utils;

public class Toggle {

	private int status = 1;

	private Callback trueEvent, falseEvent;

	public Toggle(Callback _trueEvent, Callback _falseEvent) {
		this.trueEvent = _trueEvent;
		this.falseEvent = _falseEvent;
	}
	
	public boolean getStatus() {
		if (status == 1) {
			return true;
		}
		return false;
	}

	public void toggle() {
		this.status *= -1;
	}

	public void toggleAndCallEvent() {
		this.status *= -1;

		if (this.status == 1) {
			this.trueEvent.execute();
			return;
		}

		this.falseEvent.execute();
	}

	public interface Callback {
		public void execute();
	}
}
