package comp.main.twentyfoureats;

import java.util.ArrayList;

import placesAPI.Place;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	private Button getDirections;
	public final Activity current = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		// Create the main control object
		((GlobalApplication) getApplication()).mainControl.setContext(this);
		this.mainControl = ((GlobalApplication) getApplication()).mainControl;
		// Create the getDirections button
		this.getDirections = (Button) findViewById(R.id.UseButton);
		Button Test = (Button) findViewById(R.id.TestButton);
		// Set on click
		this.getDirections.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				// Check for google play services
				if (!Control.checkForGooglePlayServices(current)) {
					Toast.makeText(mainControl.getContext(),
							"" + mainControl.getStringSetting("work"),
							Toast.LENGTH_LONG).show();
					Boolean[] show = { true, true };
					String[] dance = { "hello", "hola" , "fail" };
					mainControl.writeSettings(dance);
				} else {
					try {
						// Call to get the location
						/*
						 * mainControl.getCurrentLocation(new
						 * LocationCallback(){
						 * 
						 * @Override public void execute(Location loc) {
						 * Toast.makeText(v.getContext(), "" +
						 * loc.getLongitude() + ", " + loc.getLatitude(),
						 * Toast.LENGTH_LONG).show();
						 * Log.d("success","success"); }
						 * 
						 * });
						 */

						// Call to get the address
						/*
						 * mainControl.getCurrentAddress(new AddressCallback(){
						 * 
						 * @Override public void execute(String data) {
						 * 
						 * Toast.makeText(v.getContext(), data,
						 * Toast.LENGTH_LONG).show();
						 * 
						 * }
						 * 
						 * });
						 */
						// Control.showMap("65.9667", "-18.5333", current);
						mainControl.getListOfResteraunts(current,
								"Los Angeles", new RestListAct() {

									public void execute(ArrayList<Place> temp) {
										((GlobalApplication) getApplication()).mainControl.goToList(temp);
									}

									@Override
									public void execute(Place places) {
										// TODO Auto-generated method stub
									}

								}, null, null, null);

					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(v.getContext(), "Failure to get GPS",
								Toast.LENGTH_LONG).show();
					}

				}
			}

		});
		
		Test.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mainControl.getContext(),
						"" + mainControl.getStringSetting(Control.DEFAULT_DISTANCE),
						Toast.LENGTH_LONG).show();
			}
			
		});
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

	// Switch between current activity and new activity with a given value
	public void switchActivityWithIntent(View test) {
		CharSequence valueToPass = ((TextView) findViewById(R.id.Location))
				.getText();
		Intent switchToListAct = new Intent(this, ListView.class);
		switchToListAct.setAction("FILL_LIST");
		switchToListAct.putExtra(EXTRA_MESSAGE, valueToPass);
		startActivity(switchToListAct);
	}
}
