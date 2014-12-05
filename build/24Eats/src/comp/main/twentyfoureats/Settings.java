package comp.main.twentyfoureats;

//import placesAPI.Place;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import applic.GlobalApplication;
import control.Control;

public class Settings extends ActionBarActivity {
	// ********************************
	// private Variables
	// ********************************
	private Control mainControl;
	private ToggleButton[] toggleValues;
	private EditText[] textSettings;
	private static final String[] BOOLEAN_LIST_VALUES = new String[]{
		Control.GET_LIST_AT_STARTUP, Control.PRESET_CURRENT_LOC, Control.PRELOAD }; 
	private static final String[] STRING_LIST_VALUES = new String[]{Control.DEFAULT_DISTANCE};
	private OnClickListener settingsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			changeSettings();
		}

	};// Listener for button changes

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		// Create the main control object
		((GlobalApplication) getApplication()).mainControl.setContext(this);
		this.mainControl = ((GlobalApplication) getApplication()).mainControl;
		// Get all the toggles
		toggleValues = new ToggleButton[3];
		toggleValues[0] = (ToggleButton) this.findViewById(R.id.toggleRun);
		toggleValues[1] = null;
		toggleValues[2] = (ToggleButton) this.findViewById(R.id.togglePreload);
		// Get all the TextViews
		textSettings = new EditText[1];
		textSettings[0] = (EditText) this.findViewById(R.id.editText1);

		String value;
		for (int i = 0; i < this.toggleValues.length; i++) {
			//Set the settings
			value = this.mainControl.getStringSetting(Settings.BOOLEAN_LIST_VALUES[i]);
			if(value!=null){
				if(value.compareTo("true")==0){
					this.toggleValues[i].setChecked(true);
				}else{
					this.toggleValues[i].setChecked(false);
				}

			}
			//Add the listener
			this.toggleValues[i].setOnClickListener(this.settingsListener);
		}

		for (int i = 0; i < this.textSettings.length; i++) {
			
			value = this.mainControl.getStringSetting(Settings.STRING_LIST_VALUES[i]);
			
			if(value!=null){
				this.textSettings[i].setText(value);
			}
			
			this.textSettings[i].setOnEditorActionListener(new TextView.OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					changeSettings();
					return false;
				}
				
			});
			
			this.textSettings[i]
					.setOnFocusChangeListener(new OnFocusChangeListener() {
						
						
						
						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							if (!hasFocus) {
								changeSettings();
							}

						}
					});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form, menu);
		return true;
	}

	private void changeSettings() {
		String[] settings = new String[toggleValues.length
				+ textSettings.length];

		for (int textValues = 0; textValues < textSettings.length; textValues++) {
			settings[textValues] = textSettings[textValues].getText()
					.toString();
		}

		for (int i = textSettings.length,j=0; i < toggleValues.length; i++,j++) {
			settings[i] = isChecked(toggleValues[j]);
		}

		mainControl.setSettings(settings);
	}

	private String isChecked(ToggleButton toggle) {
		if(toggle != null){
		return (toggle.isChecked() ? "true" : "false");
		}else{
			return "false";
		}
	}
}
