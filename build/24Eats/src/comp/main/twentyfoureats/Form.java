package comp.main.twentyfoureats;

import java.util.ArrayList;

import placesAPI.Place;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import applic.GlobalApplication;
import control.Control;
import control.Control.RestListAct;

/**
 * 
 * @author David Greenberg Primary activity containing the form search call
 */
public class Form extends ActionBarActivity {
	public final static String EXTRA_MESSAGE = "Form.message";
	private Control mainControl;
	private Button useCurrentLoc;
	private Button addressButton, distButton;
	private EditText addressText, distance;
	public final Activity current = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		// Create the main control object
		((GlobalApplication) getApplication()).mainControl.setContext(this);
		this.mainControl = ((GlobalApplication) getApplication()).mainControl;
		// Create the getDirections button
		this.useCurrentLoc = (Button) findViewById(R.id.UseButton);
		this.addressButton = (Button) findViewById(R.id.LocButton);
		this.addressText = (EditText) findViewById(R.id.Location);
		this.distance = (EditText) findViewById(R.id.distance);
		this.distButton = (Button) findViewById(R.id.DistButton);

		String timeTo = this.setDefaultValues();

		if (timeTo != null
				&& this.mainControl.getStringSetting(
						Control.GET_LIST_AT_STARTUP).compareTo("true") == 0) {
			this.restrauntView(Control.NO_ADDRESS, timeTo);
		} else if (this.mainControl.getStringSetting(
				Control.GET_LIST_AT_STARTUP).compareTo("true") == 0) {
			this.restrauntView(Control.NO_ADDRESS, "10");
		}

		// Set on click
		this.addressButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restrauntWithLoc(v);
			}
		});

		this.useCurrentLoc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				restrauntWithoutLoc(v);
			}
		});

		this.distButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				restrauntWithDist(v);
			}
		});

		this.addressText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						addressButton.performClick();
						return false;
					}

				});

		this.distance
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {

						distButton.performClick();
						
						return false;
					}

				});

	}

	@Override
	protected void onResume() {
		// this.mainControl.resumeAd();
		super.onResume();
		this.mainControl.setContext(current);
		this.setDefaultValues();
	}

	@Override
	protected void onDestroy() {
		// this.mainControl.destroyAd();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// this.mainControl.pauseAd();
		super.onPause();
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	private String setDefaultValues() {
		// Get and set the preset time
		String timeTo = this.mainControl
				.getStringSetting(Control.DEFAULT_DISTANCE);
		if (timeTo != null) {
			this.distance.setText(timeTo);
		}
		return timeTo;
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form, menu);
		return true;
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			mainControl.goToSettings();
		}
		return super.onOptionsItemSelected(item);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Run with a location
	 * 
	 * @param loc
	 */
	public void restrauntWithLoc(View loc) {
		this.restrauntView(addressText.getText().toString(), null);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Run the list without a location
	 * 
	 * @param loc
	 */
	public void restrauntWithoutLoc(View loc) {
		this.restrauntView(Control.NO_ADDRESS, null);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Run with a distance value
	 * 
	 * @param loc
	 */
	public void restrauntWithDist(View loc) {
		String location = addressText.getText().toString()
				.replaceAll("\\s+", ""); // Get location if
		// it exists

		if (location.compareTo("") == 0) {
			location = Control.NO_ADDRESS; // If location doesn't exist, use
											// current location
		}
		this.restrauntView(location, this.distance.getText().toString()
				.replaceAll("\\s+", ""));
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Change to the resteraunt view
	 * 
	 * @param Loc
	 *            The location if it exists
	 * @param distance
	 *            The distance to the place if it is used
	 */
	private void restrauntView(String Loc, String distance) {
		//Hide the keyboard
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		
		// Check for google play services
		if (!Control.checkForGooglePlayServices(current)) {
			Toast.makeText(this,
					"You do not have Google Play, please install before use",
					Toast.LENGTH_LONG).show();
		} else {
			try {
				mainControl.getListOfResteraunts(current, Loc,
						new RestListAct() {

							public void execute(ArrayList<Place> temp) {
								((GlobalApplication) getApplication()).mainControl
										.goToList(temp);
							}

							@Override
							public void execute(Place places) {
								// TODO Auto-generated method stub
							}

						}, null, null, null, distance);

			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, "Failure to get GPS", Toast.LENGTH_LONG)
						.show();
			}

		}
	}

}
