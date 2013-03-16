package com.example.scoutingapp;

import utils.Constants;
import utils.DataV2;
import utils.DatabaseHelper;
import utils.MatchDatabaseHelper;
import utils.NumberPickerCustom;
import utils.QueueItem;
import utils.Toggle;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
	
	// Spinners
	Spinner 
	hangabilitySpinner = null,
	pushabilitySpinner = null,
	pickupMethodSpinner = null,
	pickupSpeedSpinner = null,
	fivePointablilitySpinner = null,
	penaltiesSpinner = null,
	defenceSpinner = null;
	
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
	DataV2 matchData = null;
	
	

	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
    protected void onCreate(Bundle _savedInstanceState) {
    	super.onCreate(_savedInstanceState);
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scouting);
        
        setup();
    }
	
	@Override
	protected void onStop() {
		super.onStop();
		// TODO: Save data
		Toast.makeText(getApplicationContext(), "Hello ", Toast.LENGTH_SHORT).show();
		SaveData saveData = new SaveData();
		saveData.execute();
	}
    
    
    
    /*===============================================
	 * Setup Methods
	 *=============================================*/
	private void setup() {
		setupDatabase();
		setupLayouts();
		setupTextViews();
		setupSpinners();
		setupVisualObjects();
		setupQueueItems();
	}
	
	private void setupQueueItems() {
		Bundle b = getIntent().getExtras();
		int matchNumber = b.getInt("match_number");
		int teamNumber = b.getInt("team_number");
		String teamColor;
		
		if (b.containsKey("team_color")) {
			teamColor = b.getString("team_color");
		}
		else {
			teamColor = QueueItem.QUEUE_ITEM_COLOR_UNKNOWN;
		}
		
		Log.w("TEST!!!!!!!!!!!!!!!", "Test::: " + teamColor);
		
		queueItem = new QueueItem(matchNumber, teamNumber, teamColor);
		
		changeAlianceColor(teamColor);
		
		matchData = new DataV2(queueItem);
		
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
    
    private void setupSpinners() {
    	hangabilitySpinner = (Spinner)findViewById(R.id.spinner_hangability);
    	hangabilitySpinner.setOnItemSelectedListener(generalSpinnerListener);
    	
    	pickupMethodSpinner = (Spinner)findViewById(R.id.spinner_pickupability);
    	pickupMethodSpinner.setOnItemSelectedListener(generalSpinnerListener);
    }
    
    
    
    /*===============================================
	 * Visusal Related Methods
	 *=============================================*/
    private void changeAlianceColor(String _color) {
    	if (_color.equals(QueueItem.QUEUE_ITEM_COLOR_RED_STRING)) {
    		scoresRelativeLayout.setBackgroundColor(getResources().getColor(R.color.red_alliance));
    	}
    	else if (_color.equals(QueueItem.QUEUE_ITEM_COLOR_BLUE_STRING)) {
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
    			matchData.teleopScoreTop++;
    			scoreHighNumberPicker.setValue(matchData.teleopScoreTop);
    		}
    		else {
    			matchData.autonomousScoreTop++;
    			scoreHighNumberPicker.setValue(matchData.autonomousScoreTop);
    		}
    	}
    	else if (_id == scoreMiddleLeftButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScoreMiddleLeft++;
    			scoreMiddleLeftNumberPicker.setValue(matchData.teleopScoreMiddleLeft);
    		}
    		else {
    			matchData.autonomousScoreMiddleLeft++;
    			scoreMiddleLeftNumberPicker.setValue(matchData.autonomousScoreMiddleLeft);
    		}
    	}
    	else if (_id == scoreMiddleRightButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScoreMiddleRight++;
    			scoreMiddleRightNumberPicker.setValue(matchData.teleopScoreMiddleRight);
    		}
    		else {
    			matchData.autonomousScoreMiddleRight++;
    			scoreMiddleRightNumberPicker.setValue(matchData.autonomousScoreMiddleRight);
    		}
    	}
    	else if (_id == scoreLowButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopScoreLow++;
    			scoreLowNumberPicker.setValue(matchData.teleopScoreLow);
    		}
    		else {
    			matchData.autonomousScoreLow++;
    			scoreLowNumberPicker.setValue(matchData.autonomousScoreLow);
    		}
    	}
    	else if (_id == missedButton.getId()) {
    		if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
    			matchData.teleopMisses++;
    			missedNumberPicker.setValue(matchData.teleopMisses);
    		}
    		else {
    			matchData.autonomousMisses++;
    			missedNumberPicker.setValue(matchData.autonomousMisses);
    		}
    	}
    }
    
    private void toggleScoresBasedOnMode(boolean _mode) {
    	if (_mode == Constants.TELEOP_STATUS) {
    		scoreHighNumberPicker.setValue(matchData.teleopScoreTop);
    		scoreMiddleLeftNumberPicker.setValue(matchData.teleopScoreMiddleLeft);
    		scoreMiddleRightNumberPicker.setValue(matchData.teleopScoreMiddleRight);
    		scoreLowNumberPicker.setValue(matchData.teleopScoreLow);
    		missedNumberPicker.setValue(matchData.teleopMisses);
    	}
    	else {
    		scoreHighNumberPicker.setValue(matchData.autonomousScoreTop);
    		scoreMiddleLeftNumberPicker.setValue(matchData.autonomousScoreMiddleLeft);
    		scoreMiddleRightNumberPicker.setValue(matchData.autonomousScoreMiddleRight);
    		scoreLowNumberPicker.setValue(matchData.autonomousScoreLow);
    		missedNumberPicker.setValue(matchData.autonomousMisses);
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
					matchData.teleopScoreTop = newVal;
	    		}
				else {
					matchData.autonomousScoreTop = newVal;
	    		}
			}
			else if (picker.getId() == scoreMiddleLeftNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
					matchData.teleopScoreMiddleLeft = newVal;
	    		}
				else {
					matchData.autonomousScoreMiddleLeft = newVal;
	    		}
			}
			else if (picker.getId() == scoreMiddleRightNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
					matchData.teleopScoreMiddleRight = newVal;
	    		}
				else {
					matchData.autonomousScoreMiddleRight = newVal;
	    		}
			}
			else if (picker.getId() == scoreLowNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
					matchData.teleopScoreLow = newVal;
	    		}
				else {
					matchData.autonomousScoreLow = newVal;
	    		}
			}
			else if (picker.getId() == missedNumberPicker.getId()) {
				if (modeButtonToggle.getStatus() == Constants.TELEOP_STATUS) {
					matchData.teleopMisses = newVal;
	    		}
				else {
					matchData.autonomousMisses = newVal;
	    		}
			}
		}
		
	};
	
	
	Spinner.OnItemSelectedListener generalSpinnerListener = new Spinner.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			String data = parent.getItemAtPosition(pos).toString();
			if (id == hangabilitySpinner.getId()) {
				matchData.climb = data;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	/*===============================================
	 * Async Tasks
	 *=============================================*/
	class SaveData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			MatchDatabaseHelper dbHelper = MatchDatabaseHelper.getInstance(1);
			Log.i("Log Test!!!!!!!!", dbHelper.toString());
			
			return null;
		}
		
	};
	
}
