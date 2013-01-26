package com.example.scoutingapp;

import utils.Constants;
import utils.DataTypes;
import utils.DatabaseHelper;
import utils.MatchData;
import utils.NumberPickerCustom;
import utils.QueueItem;
import utils.Toggle;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutingapp.R.color;


public class ScoutingActivity extends Activity {

	/*===============================================
	 * Visual Objects
	 *=============================================*/
	// Layouts
	RelativeLayout scoresRelativeLayout = null;
	
	// Buttons
	Button modeButton = null;
	Button scoreHighButton = null;
	Button scoreMiddleLeftButton = null;
	Button scoreMiddleRightButton = null;
	Button scoreLowButton = null;
	Button missedButton = null;
	
	// NumberPickers
	NumberPickerCustom scoreHighNumberPicker = null;
	NumberPickerCustom scoreMiddleLeftNumberPicker = null;
	NumberPickerCustom scoreMiddleRightNumberPicker = null;
	NumberPickerCustom scoreLowNumberPicker = null;
	NumberPickerCustom missedNumberPicker = null;
	
	// TextViews
	TextView matchNumberTextView = null;
	TextView teamNumberTextView = null;
	
	
	
	/*===============================================
	 * Database Varables
	 *=============================================*/
	DatabaseHelper databaseHelper = null;
	
	
	
	/*===============================================
	 * Logical Variables
	 *=============================================*/
	QueueItem queueItem = null;
	MatchData matchData = null;
	
	

	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
    protected void onCreate(Bundle _savedInstanceState) {
    	super.onCreate(_savedInstanceState);
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        setup();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		// TODO: Save data
		Toast.makeText(getApplicationContext(), "Hello ", Toast.LENGTH_SHORT).show();
	}
    
    
    
    /*===============================================
	 * Setup Methods
	 *=============================================*/
	private void setup() {
		setupDatabase();
		setupLayouts();
		setupTextViews();
		setupVisualObjects();
		setupQueueItems();
	}
	
	private void setupQueueItems() {
		Bundle b = getIntent().getExtras();
		int matchNumber = b.getInt("match_number");
		int teamNumber = b.getInt("team_number");
		int teamColor;
		if (b.containsKey("team_color")) {
			teamColor = b.getInt("team_color");
		} else {
			teamColor = QueueItem.QUEUE_ITEM_COLOR_UNKNOWN;
		}
		
		Log.w("TEST!!!!!!!!!!!!!!!", "Test::: " + teamColor);
		
		queueItem = new QueueItem(matchNumber, teamNumber, teamColor);
		
		changeAlianceColor(teamColor);
		
		matchData = new MatchData(queueItem);
		
		teamNumberTextView.setText(Integer.toString(queueItem.teamNumber));
		matchNumberTextView.setText(Integer.toString(queueItem.matchNumber));
	}
	
	private void setupTextViews() {
		matchNumberTextView = (TextView)findViewById(R.id.text_view_match_number);
		teamNumberTextView = (TextView)findViewById(R.id.text_view_team);
	}
	
	private void setupLayouts() {
		scoresRelativeLayout = (RelativeLayout)findViewById(R.id.relative_layout_scores);
	}
	
	private void setupDatabase() {
		databaseHelper = new DatabaseHelper(this.getApplicationContext());
	}
	
    private void setupVisualObjects() {
        setupButtons();
        setupNumberPickers();
    }
    
    private void setupButtons() {
    	modeButton = (Button)findViewById(R.id.button_mode);
        modeButton.setOnClickListener(modeButtonOnClickListener);
        
        scoreHighButton = (Button)findViewById(R.id.button_score_high);
        scoreHighButton.setOnClickListener(scoreGeneralButtonOnClickListener);
        
        scoreMiddleLeftButton = (Button)findViewById(R.id.button_score_middle_left);
        scoreMiddleLeftButton.setOnClickListener(scoreGeneralButtonOnClickListener);
        
        scoreMiddleRightButton = (Button)findViewById(R.id.button_score_middle_right);
        scoreMiddleRightButton.setOnClickListener(scoreGeneralButtonOnClickListener);
        
        scoreLowButton = (Button)findViewById(R.id.button_score_low);
        scoreLowButton.setOnClickListener(scoreGeneralButtonOnClickListener);
        
        missedButton = (Button)findViewById(R.id.button_missed_shots);
        missedButton.setOnClickListener(scoreGeneralButtonOnClickListener);
    }
    
    private void setupNumberPickers() {
    	// The setDescendantFocusablility method stops the keyboard form showing up
    	
    	scoreHighNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_high);
    	scoreHighNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
    	scoreHighNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	
    	scoreMiddleLeftNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_middle_left);
    	scoreMiddleLeftNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
    	scoreMiddleLeftNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	
    	scoreMiddleRightNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_middle_right);
    	scoreMiddleRightNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
    	scoreMiddleRightNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	
    	scoreLowNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_low);
    	scoreLowNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
    	scoreLowNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	
    	missedNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_missed_shots);
    	missedNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
    	missedNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }
    
    
    
    /*===============================================
	 * Visusal Related Methods
	 *=============================================*/
    private void changeAlianceColor(int _color) {
    	if (_color == QueueItem.QUEUE_ITEM_COLOR_RED) {
    		scoresRelativeLayout.setBackgroundColor(getResources().getColor(R.color.red_alliance));
    	}
    	else if (_color == QueueItem.QUEUE_ITEM_COLOR_BLUE) {
    		scoresRelativeLayout.setBackgroundColor(getResources().getColor(R.color.blue_alliance));
    	}
    	else {
    		scoresRelativeLayout.setBackgroundColor(getResources().getColor(R.color.black_overlay));
    	}
    }
    
    
    
    /*===============================================
	 * Score Related Methods
	 *=============================================*/
    private void handleScoreClick(int _id) {
    	if (_id == scoreHighButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScores[Constants.SCORE_HIGH]++;
    			scoreHighNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_HIGH]);
    		} else {
    			matchData.scoresAutonomous[DataTypes.SCORE_AUTONOMOUS_TOP_INDEX].setData(matchData.scoresAutonomous[DataTypes.SCORE_AUTONOMOUS_TOP_INDEX].getData() + 1);
    			scoreHighNumberPicker.setValue(matchData.scoresAutonomous[DataTypes.SCORE_AUTONOMOUS_TOP_INDEX].getData());
    		}
    	}
    	else if (_id == scoreMiddleLeftButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScores[Constants.SCORE_MIDDLE_LEFT]++;
    			scoreMiddleLeftNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_MIDDLE_LEFT]);
    		} else {
    			matchData.autonomousScores[Constants.SCORE_MIDDLE_LEFT]++;
    			scoreMiddleLeftNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_MIDDLE_LEFT]);
    		}
    	}
    	else if (_id == scoreMiddleRightButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScores[Constants.SCORE_MIDDLE_RIGHT]++;
    			scoreMiddleRightNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_MIDDLE_RIGHT]);
    		} else {
    			matchData.autonomousScores[Constants.SCORE_MIDDLE_RIGHT]++;
    			scoreMiddleRightNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_MIDDLE_RIGHT]);
    		}
    	}
    	else if (_id == scoreLowButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScores[Constants.SCORE_LOW]++;
    			scoreLowNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_LOW]);
    		} else {
    			matchData.autonomousScores[Constants.SCORE_LOW]++;
    			scoreLowNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_LOW]);
    		}
    	}
    	else if (_id == missedButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScores[Constants.MISSED_SHOTS]++;
    			missedNumberPicker.setValue(matchData.teleopScores[Constants.MISSED_SHOTS]);
    		} else {
    			matchData.autonomousScores[Constants.MISSED_SHOTS]++;
    			missedNumberPicker.setValue(matchData.autonomousScores[Constants.MISSED_SHOTS]);
    		}
    	}
    }
    
    private void toggleScoresBasedOnMode(boolean _mode) {
    	if (_mode == Constants.TELEOP_STATUS) {
    		scoreHighNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_HIGH]);
    		scoreMiddleLeftNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_MIDDLE_LEFT]);
    		scoreMiddleRightNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_MIDDLE_RIGHT]);
    		scoreLowNumberPicker.setValue(matchData.teleopScores[Constants.SCORE_LOW]);
    		missedNumberPicker.setValue(matchData.teleopScores[Constants.MISSED_SHOTS]);
    	}
    	else {
    		scoreHighNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_HIGH]);
    		scoreMiddleLeftNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_MIDDLE_LEFT]);
    		scoreMiddleRightNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_MIDDLE_RIGHT]);
    		scoreLowNumberPicker.setValue(matchData.autonomousScores[Constants.SCORE_LOW]);
    		missedNumberPicker.setValue(matchData.autonomousScores[Constants.MISSED_SHOTS]);
    	}
    }
    
    
    
    /*===============================================
	 * Toggles
	 *=============================================*/
    Toggle.Callback modeButtonTrueCallback = new Toggle.Callback() {
		@Override
		public void execute() {
			modeButton.setBackgroundResource(color.button_mode_autonomous);
			modeButton.setText(R.string.button_mode_autonomous);
		}
	};
	
	Toggle.Callback modeButtonFalseCallback = new Toggle.Callback() {
		@Override
		public void execute() {
			modeButton.setBackgroundResource(color.button_mode_teleop);
			modeButton.setText(R.string.button_mode_teleop);
		}
	};
	
	Toggle modeButtonToggle = new Toggle(modeButtonTrueCallback, modeButtonFalseCallback);

	
	
	/*===============================================
	 * Button Listeners
	 *=============================================*/
    Button.OnClickListener modeButtonOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			modeButtonToggle.toggleAndCallEvent();
			toggleScoresBasedOnMode(modeButtonToggle.getStatus());
		}
	};
	
	Button.OnClickListener scoreGeneralButtonOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			handleScoreClick(v.getId());
		}
	};
	
	
	
	/*===============================================
	 * NumberPicker Listeners
	 *=============================================*/
	NumberPickerCustom.ValueChangeListener scoreGeneralNumberPickerOnValueChangeListener = new NumberPickerCustom.ValueChangeListener() {

		@Override
		public void onNumberPickerValueChange(NumberPickerCustom picker, int newVal) {
			if (picker.getId() == scoreHighNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
	    			matchData.teleopScores[Constants.SCORE_HIGH] = newVal;
	    		} else {
	    			matchData.scoresAutonomous[DataTypes.SCORE_AUTONOMOUS_TOP_INDEX].setData(newVal);
	    		}
			}
			else if (picker.getId() == scoreMiddleLeftNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
	    			matchData.teleopScores[Constants.SCORE_MIDDLE_LEFT] = newVal;
	    		} else {
	    			matchData.autonomousScores[Constants.SCORE_MIDDLE_LEFT] = newVal;
	    		}
			}
			else if (picker.getId() == scoreMiddleRightNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
	    			matchData.teleopScores[Constants.SCORE_MIDDLE_RIGHT] = newVal;
	    		} else {
	    			matchData.autonomousScores[Constants.SCORE_MIDDLE_RIGHT] = newVal;
	    		}
			}
			else if (picker.getId() == scoreLowNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
	    			matchData.teleopScores[Constants.SCORE_LOW] = newVal;
	    		} else {
	    			matchData.autonomousScores[Constants.SCORE_LOW] = newVal;
	    		}
			}
		}
		
	};
	
	
	
	/*===============================================
	 * Async Tasks
	 *=============================================*/
	private class AsyncDatabaseUpdate extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			
			//TODO: update / create entry
			
			return true;
		}
		
	}
	
}
