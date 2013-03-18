package com.example.scoutingapp;

import utils.Constants;
import utils.DBHelperV2;
import utils.DataV2;
import utils.NumberPickerCustom;
import utils.QueueItem;
import utils.Toggle;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scoutingapp.R.color;


public class ScoutingActivity extends Activity {

	/*===============================================
	 * Visual Objects
	 *=============================================*/
	// Layouts
	RelativeLayout scoresRelativeLayout = null;

	// Buttons
	Button
	modeButton = null,
	scoreHighButton = null,
	scoreMiddleLeftButton = null,
	scoreMiddleRightButton = null,
	scoreLowButton = null,
	missedButton = null;

	// NumberPickers
	NumberPickerCustom
	scoreHighNumberPicker = null,
	scoreMiddleLeftNumberPicker = null,
	scoreMiddleRightNumberPicker = null,
	scoreLowNumberPicker = null,
	missedNumberPicker = null;

	// Spinners
	Spinner
	hangabilitySpinner = null,
	pushabilitySpinner = null,
	pickupMethodSpinner = null,
	pickupSpeedSpinner = null,
	fivePointablilitySpinner = null,
	penaltiesSpinner = null,
	defenceSpinner = null;

	// EditTexts
	EditText
	notesEditText = null;

	// TextViews
	TextView 
	matchNumberTextView = null,
	teamNumberTextView = null;



	/*===============================================
	 * Database Varables
	 *=============================================*/
	DBHelperV2 dbHelper = null;



	/*===============================================
	 * Logical Variables
	 *=============================================*/
	QueueItem queueItem = null;
	DataV2 matchData = null;
	int tabletNumber = 0;



	/*===============================================
	 * Activity Methods
	 *=============================================*/
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scouting);

		Bundle b = getIntent().getExtras();
		tabletNumber = b.getInt("tabletNumber");
		Log.d("TABLET NUMBER!!!!!!!! " + tabletNumber, "TABLET NUMBER!!!!!!!! " + tabletNumber);

		setup();
	}

	@Override
	protected void onStop() {
		super.onStop();
//		Toast.makeText(getApplicationContext(), "Hello ", Toast.LENGTH_SHORT).show();
		
		matchData.notes = notesEditText.getText().toString();
		
		Log.i("Log Test!!!!!!!!", dbHelper.toString());
		dbHelper.insert(matchData);
		finish();
	}



	/*===============================================
	 * Setup Methods
	 *=============================================*/
	/**
	 * Invoke all the setup methods
	 */
	private void setup() {
		setupDatabase();
		setupLayouts();
		setupQueueItems();
		setupVisualObjects();
	}

	/**
	 * Get the info about the match
	 */
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

		DataV2 tmp = dbHelper.getMatchData(queueItem);
		if (tmp == null)
			matchData = new DataV2(queueItem);
		else
			matchData = tmp;

	}

	/**
	 * Setup Text Views
	 */
	private void setupTextViews() {
		matchNumberTextView = (TextView)findViewById(R.id.text_view_match_number);
		teamNumberTextView = (TextView)findViewById(R.id.text_view_team);
		
		teamNumberTextView.setText(Integer.toString(queueItem.teamNumber));
		matchNumberTextView.setText(Integer.toString(queueItem.matchNumber));
	}

	/**
	 * Setup Layout
	 */
	private void setupLayouts() {
		scoresRelativeLayout = (RelativeLayout)findViewById(R.id.relative_layout_scores);
	}

	/**
	 * Setup the database
	 */
	private void setupDatabase() {
		dbHelper = new DBHelperV2(this, tabletNumber);
	}

	/**
	 * Setup Visual inputs
	 */
	private void setupVisualObjects() {
		setupTextViews();
		setupSpinners();
		setupButtons();
		setupNumberPickers();
		setupEditTexts();
	}

	/**
	 * Setup buttons
	 */
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

	/**
	 * Setup number pickers
	 */
	private void setupNumberPickers() {
		// The setDescendantFocusablility method stops the keyboard form showing up

		scoreHighNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_high);
		scoreHighNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
		scoreHighNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		scoreHighNumberPicker.setValue(matchData.autonomousScoreTop);
		
		scoreMiddleLeftNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_middle_left);
		scoreMiddleLeftNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
		scoreMiddleLeftNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		scoreMiddleLeftNumberPicker.setValue(matchData.autonomousScoreMiddleLeft);
		
		scoreMiddleRightNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_middle_right);
		scoreMiddleRightNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
		scoreMiddleRightNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		scoreMiddleRightNumberPicker.setValue(matchData.autonomousScoreMiddleRight);
		
		scoreLowNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_score_low);
		scoreLowNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
		scoreLowNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		scoreLowNumberPicker.setValue(matchData.autonomousScoreLow);
		
		missedNumberPicker = (NumberPickerCustom)findViewById(R.id.number_picker_missed_shots);
		missedNumberPicker.setOnValueChangeListener(scoreGeneralNumberPickerOnValueChangeListener);
		missedNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		missedNumberPicker.setValue(matchData.autonomousMisses);
	}

	/**
	 * Setup spinners
	 */
	@SuppressWarnings("unchecked")
	private void setupSpinners() {
		ArrayAdapter<String> a = null;
		int pos = 0;
		
		hangabilitySpinner = (Spinner)findViewById(R.id.spinner_hangability);
		hangabilitySpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) hangabilitySpinner.getAdapter();
		pos = a.getPosition(matchData.climb);
		hangabilitySpinner.setSelection(pos);

		pushabilitySpinner = (Spinner)findViewById(R.id.spinner_pushability);
		pushabilitySpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) pushabilitySpinner.getAdapter();
		pos = a.getPosition(matchData.push);
		pushabilitySpinner.setSelection(pos);

		pickupMethodSpinner = (Spinner)findViewById(R.id.spinner_pickupability);
		pickupMethodSpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) pickupMethodSpinner.getAdapter();
		pos = a.getPosition(matchData.pickupMethod);
		pickupMethodSpinner.setSelection(pos);

		pickupSpeedSpinner = (Spinner)findViewById(R.id.spinner_pickupspeed);
		pickupSpeedSpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) pickupSpeedSpinner.getAdapter();
		pos = a.getPosition(matchData.pickupSpeed);
		pickupSpeedSpinner.setSelection(pos);
		
		fivePointablilitySpinner = (Spinner)findViewById(R.id.spinner_fivepointability);
		fivePointablilitySpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) fivePointablilitySpinner.getAdapter();
		pos = a.getPosition(matchData.fivePointScore);
		fivePointablilitySpinner.setSelection(pos);

		penaltiesSpinner = (Spinner)findViewById(R.id.spinner_penalties);
		penaltiesSpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) penaltiesSpinner.getAdapter();
		pos = a.getPosition(matchData.penalties);
		penaltiesSpinner.setSelection(pos);

		defenceSpinner = (Spinner)findViewById(R.id.spinner_defence);
		defenceSpinner.setOnItemSelectedListener(generalSpinnerListener);
		a = (ArrayAdapter<String>) defenceSpinner.getAdapter();
		pos = a.getPosition(matchData.defence);
		defenceSpinner.setSelection(pos);
	}
	
	/**
	 * Setup edit texts
	 */
	private void setupEditTexts() {
		// TODO: Make sticky
		notesEditText = (EditText)findViewById(R.id.edit_text_defence);
		notesEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				matchData.notes = s.toString();
			}
		});
		notesEditText.setText(matchData.notes);
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


	/*===============================================
	 * Spinner Listener
	 *=============================================*/
	Spinner.OnItemSelectedListener generalSpinnerListener = new Spinner.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id2) {
			int id = parent.getId();
			String data = parent.getItemAtPosition(pos).toString();
			if (id == hangabilitySpinner.getId())
				matchData.climb = data;
			else if (id == pushabilitySpinner.getId())
				matchData.push = data;
			else if (id == pickupMethodSpinner.getId())
				matchData.pickupMethod = data;
			else if (id == pickupSpeedSpinner.getId())
				matchData.pickupSpeed = data;
			else if (id == fivePointablilitySpinner.getId())
				matchData.fivePointScore = data;
			else if (id == penaltiesSpinner.getId())
				matchData.penalties = data;
			else if (id == defenceSpinner.getId())
				matchData.defence = data;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	};

}
