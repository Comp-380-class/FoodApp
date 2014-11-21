package comp.main.twentyfoureats;

import java.util.ArrayList;

import placesAPI.Place;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
	private Button addressButton;
	private EditText addressText;
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
		this.addressButton = (Button) findViewById(R.id.DistButton);
		this.addressText = (EditText) findViewById(R.id.Location);
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
	/*public void switchActivityWithIntent(View test) {
		CharSequence valueToPass = ((TextView) findViewById(R.id.Location))
				.getText();
		Intent switchToListAct = new Intent(this, ListView.class);
		switchToListAct.setAction("FILL_LIST");
		switchToListAct.putExtra(EXTRA_MESSAGE, valueToPass);
		startActivity(switchToListAct);
	}*/
	
	/**
	 * Run with a location
	 * @param loc
	 */
	public void restrauntWithLoc(View loc){
		this.restrauntView(addressText.getText().toString());
	}
	/**
	 * Run the list without a location
	 * @param loc
	 */
	public void restrauntWithoutLoc(View loc){
		this.restrauntView(Control.NO_ADDRESS);
	}
	/*
	 * Change to the restraunt view with loc
	 */
	private void restrauntView(String Loc){
		// Check for google play services
		if (!Control.checkForGooglePlayServices(current)) {
			Toast.makeText(this, "You do not have Google Play, please install before use", Toast.LENGTH_LONG).show();
		} else {
			try {
				mainControl.getListOfResteraunts(current,
						Loc, new RestListAct() {

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
				Toast.makeText(this, "Failure to get GPS",
						Toast.LENGTH_LONG).show();
			}

		}
	}
	
}
