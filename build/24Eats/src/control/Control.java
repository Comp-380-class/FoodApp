package control;

import google_maps_api.AddressCallback;
import google_maps_api.LocationToAddress;
import google_maps_api.MapsAPI;
import google_maps_api.MapsAPI.LocationCallback;
import google_maps_api.MapsAPI.NoGPSException;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import comp.main.twentyfoureats.R;

/**
 * 
 * @author David Greenberg Class for controlling all other underlying classes
 * 
 */
public class Control {
	private MapsAPI gMaps;
	private LocationToAddress getAddress;
	private Activity parentActivity;
	
	/**
	 * Primary constructor for the control object. Creates a new Control
	 * containing information about the activity.
	 * 
	 * @param activity
	 *            The current activity in which the program is operating
	 */
	public Control(Activity activity) {
		this.parentActivity = activity;
		gMaps = new MapsAPI(activity);
		getAddress = new LocationToAddress(activity);
	}

	/**
	 * Get the user's address based on their gps coordinates
	 * 
	 * @return The user's current location as a string
	 */
	public void getCurrentAddress(AddressCallback... callback) {
		(new getNewAddress(callback)).execute();
	}

	/*
	 * Get a user's current location as a Location object.
	 * 
	 * @return The user's current location as a location object
	 */
	public void getCurrentLocation(LocationCallback... callback)
			throws NoGPSException {
		(new getNewLocation(callback)).execute();
	}

	/**
	 * Get a location based on a provided address
	 * 
	 * @param Address
	 *            The address to be anaylzed
	 * @return The address as a Location object
	 */
	public Location getCurrentLocation(String address) {
		return null;
	}

	/**
	 * Get a list of Resteraunts in the area. When finished will transfer to the
	 * list page of the app
	 * 
	 * @param context
	 *            The context in which the object is being accessed from
	 * @param currentLocation
	 *            The current location of the user as a string
	 */
	public void getListOfResteraunts(Context context, String currentLocation,
			String... options) {
		// Add functions to get location based on given values
		(new getLocationFromAddress(options, new AddressCallback() {

			@Override
			public void execute(String data) {
				// TODO Auto-generated method stub

			}

		})).execute(options);
	}

	/**
	 * Perform when the application goes to suspend
	 * 
	 * @param acitivty
	 *            The activity which is currently displayed
	 */
	public void suspend(Activity acitivty) {
		// Add a disconnect for the location provider

	}

	// ********************************************
	// Private Classes
	// ********************************************

	/**
	 * 
	 * @author David Get a Location object from a class
	 */
	private class getLocationFromAddress extends
			AsyncTask<String, Void, List<Address>> {

		private AddressCallback[] callback;
		private String[] options;
		private int pathway; // Variable to decide path to travel down

		/**
		 * Get the current Location from an address and get the values
		 * associated with it
		 * 
		 * @param options
		 *            The options which the user chose to set in his search
		 * @param callback
		 *            The object containing a function which should execute when
		 *            the async is completed (i.e. ui thread work)
		 */
		public getLocationFromAddress(String[] options,
				AddressCallback... callback) {
			this.callback = callback;
			this.options = options;
		}

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// Get Progress bar
			onStartAsync(parentActivity);
			super.onPreExecute();
		}

		@Override
		protected List<Address> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Address> temp = null;

			for (int i = 0; i < params.length; i++) {
				try {
					temp = getAddress.getLocation(params[i]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return temp;

		}

		@Override
		protected void onPostExecute(List<Address> result) {
			Control.onStopAsync(parentActivity);
			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				// this.callback[i].execute(result);
			}
		}

	}

	/**
	 * 
	 * @author David Get GPS Location from gps and return an address
	 */
	private class getNewAddress extends AsyncTask<Void, Location, String> {
		private ProgressBar currentProgress;
		private Location temp = null; // Create location to use in return
		private MapsAPI currentMapsAPI;
		private AddressCallback[] callback;

		public getNewAddress(AddressCallback... callback) {
			this.callback = callback;
		}

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// Get Progress bar
			onStartAsync(parentActivity);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				return gMaps.getCurrentAddress();
			} catch (NoGPSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Location... result) {

		}

		@Override
		protected void onPostExecute(String result) {
			onStopAsync(parentActivity);
			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				this.callback[i].execute(result);
			}
			// Disconnect from the gps
			gMaps.disconnect();
		}

	}

	/**
	 * 
	 * @author David 
	 * Get the user's current Location as a Location object
	 */
	private class getNewLocation extends AsyncTask<MapsAPI, Location, Location> {
		private Location temp = null; // Create location to use in return
		private MapsAPI currentMapsAPI;
		private LocationCallback[] callback;

		public getNewLocation(LocationCallback... callback) {
			this.callback = callback;
		}

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// Get Progress bar
			Control.onStartAsync(parentActivity);
			super.onPreExecute();
		}

		@Override
		protected Location doInBackground(MapsAPI... params) {
			try {
				return gMaps.getCurrentGPSCoordinates();
			} catch (NoGPSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Location... result) {

		}

		@Override
		protected void onPostExecute(Location result) {

			onStopAsync(parentActivity);
			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				this.callback[i].execute(result);
			}
			// Disconnect from the gps
			currentMapsAPI.disconnect();
		}

	}

	/**
	 * 
	 * @author David
	 * Async retrieve a list of resteraunts, return an intent 
	 */
	private class getList extends AsyncTask<RestListAct,Void,Intent>{
		
		private RestListAct[] activityList;
		
		@Override 
		protected void onPreExecute(){
			onStartAsync(parentActivity);
			super.onPreExecute();
			
		}

		@Override
		protected Intent doInBackground(RestListAct... params) {
			activityList = params;
			return null;
		}
		
	}
	
	// **************************
	// Static Functions
	// **************************

	/*
	 * Start the spinner progress bar
	 */
	public static void onStartAsync(Activity parentActivity) {
		((ProgressBar) parentActivity.findViewById(R.id.spinnerProgress))
				.setVisibility(View.VISIBLE);
	}

	// End the spinner progress bar
	public static void onStopAsync(Activity parentActivity) {
		((ProgressBar) parentActivity.findViewById(R.id.spinnerProgress))
				.setVisibility(View.GONE);
	}

	/**
	 * 
	 * @author David
	 *
	 */
	public interface RestListAct{
		
		//public void execute(ArrayList<Place>);
		
	}
	
	/*
	 * Check to see if the current device has google play services installed
	 * 
	 * @return True if the software is installed, false otherwise
	 */
	public static boolean checkForGooglePlayServices(Activity activity) {
		int return_code = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(activity);

		if (return_code == ConnectionResult.SUCCESS) {
			// Everything is succesful, return true
			return true;
		} else if (return_code == ConnectionResult.SERVICE_MISSING) {
			// The service is not installed, recommend installation
			return false;
		} else if (return_code == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
			// The service is out of date, recommend updating the service
			return false;
		} else if (return_code == ConnectionResult.SERVICE_DISABLED) {
			// The service is not connected, throw alert telling them to enable
			// service
			return false;
		} else if (return_code == ConnectionResult.SERVICE_INVALID) {
			// Service is invalid, return false
		}

		// None of the above match, something else must be wrong
		return false;
	}

}
