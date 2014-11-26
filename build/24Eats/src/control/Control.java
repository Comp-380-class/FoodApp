package control;

import google_maps_api.AddressCallback;
import google_maps_api.LocationToAddress;
import google_maps_api.MapsAPI;
import google_maps_api.MapsAPI.LocationCallback;
import google_maps_api.MapsAPI.NoGPSException;

import java.util.ArrayList;
import java.util.List;

import placesAPI.Place;
import placesAPI.PlacesAPI;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import comp.main.twentyfoureats.ListItems;
import comp.main.twentyfoureats.ListView;
import comp.main.twentyfoureats.R;

/**
 * Primary Control object for the entire project. All underlying functions are
 * called and implemented through here
 * 
 * @author David Greenberg
 * 
 * @version 0.0.4
 */
public class Control {

	// ********************************
	// Final Variables
	// ********************************
	public final static boolean DEBUG = false;
	public static final String NO_ADDRESS = "NO_ADDRESS";
	public static final String GET_MORE = "GET_MORE";
	public final static String GET_LIST_AT_STARTUP = "RunAtStartUp";
	public static final String BLACK_LIST = "BlackList";
	public static final String DEFAULT_DISTANCE = "DefaultDistance";
	public static final String PRESET_CURRENT_LOC = "CurrentLoc";
	public static final String REST_LIST = "REST_LIST";
	private static final String PRELOAD = "PRELOAD";
	private static final String[] STRING_LIST_VALUES = { DEFAULT_DISTANCE,
			GET_LIST_AT_STARTUP, PRESET_CURRENT_LOC, PRELOAD };
	@SuppressWarnings("unused")
	private static final String STRING_LIST = "RUN_AT_STARTUP, PRESET_CURRENT_LOC,DEFAULT_DISTANCE,PRELOAD";
	
	private static final String PLACES_LIST = "PLACES_LIST";
	private String[] defaults = new String[] { "false", "false", "5", "false" }; // RUN_AT_STARTUP,
																					// PRESET_CURRENT_LOC,DEFAULT_DISTANCE,PRELOAD

	private List<Place> Rest_Places;// Rest Places
	// ********************************
	// Private Variables
	// ********************************
	private MapsAPI gMaps; // Maps objects
	private LocationToAddress getAddress; // Address translation object
	private Activity parentActivity; // Reference to the currently displayed
										// activity
	private PlacesAPI places; // Places api

	private SharedPreferences settings;
	private SharedPreferences.Editor settingsEditor;

	// ********************************
	// Constructors
	// ********************************

	/**
	 * Default Constructor
	 */
	public Control() {

	}

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
		places = new PlacesAPI();
	}

	// ********************************
	// Functions
	// ********************************

	/**
	 * Get the user's address based on their gps coordinates
	 * 
	 * @param callback
	 *            Function to be called at completion
	 * @return The user's current location as a string
	 */
	public void getCurrentAddress(AddressCallback... callback) {
		if (Control.checkForGooglePlayServices(parentActivity)
				&& Control.checkForGPS(parentActivity)) {
			(new getNewAddress(callback)).execute();
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/*
	 * Get a user's current location as a Location object.
	 * 
	 * @param callback Function to be called at completion
	 * 
	 * @return The user's current location as a location object
	 */
	public void getCurrentLocation(LocationCallback... callback)
			throws NoGPSException {
		if (Control.checkForGooglePlayServices(parentActivity)
				&& Control.checkForGPS(parentActivity)) {
			(new getNewLocation(callback)).execute();
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Get a location based on a provided address
	 * 
	 * @param Address
	 *            A list of possible Location addresses
	 * @param list
	 *            A list of functions which will be called once the data has
	 *            been retrieved
	 */
	public void getCurrentLocation(String address, AddressList... list) {
		(new getAddressLocation(list)).execute(address);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Get a list of Resteraunts in the area. When finished will transfer to the
	 * list page of the app
	 * 
	 * @param context
	 *            The context in which the object is being accessed from
	 * @param currentLocation
	 *            The current location of the user as a string
	 * @param callback
	 *            Function which will be called at completion
	 * @param options
	 *            List of options which will be passed to the request
	 */
	public void getListOfResteraunts(Context context, String currentLocation,
			RestListAct callback, String... options) {

		if (currentLocation == Control.NO_ADDRESS && !this.gMaps.gpsOn()) {
			buildAlertMessageNoGps(
					"Your GPS seems to be disabled, do you want to enable it?",
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		} else if (!isConnectingToInternet()) {
			buildAlertMessageNoGps(
					"Your Internet seems to be off, would you like to check it?",
					android.provider.Settings.ACTION_WIFI_SETTINGS);
		} else {
			// Add functions to get location based on given values
			(new getList(options, callback)).execute(currentLocation);
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************
	private boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) this.parentActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	private void buildAlertMessageNoGps(String message, final String intent) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				this.parentActivity);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									@SuppressWarnings("unused") final DialogInterface dialog,
									@SuppressWarnings("unused") final int id) {
								parentActivity
										.startActivity(new Intent(intent));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							@SuppressWarnings("unused") final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	public void getMoreResteraunts(Context context, RestListAct callback) {
		(new getList(callback)).execute(Control.GET_MORE);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************
	/**
	 * Get the details of the provided places object
	 * 
	 * @param places
	 *            The place whose details are desired
	 */
	public void getDetails(Place places) {
		(new GetDetails()).execute(places);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Perform when the application goes to suspend
	 * 
	 * @param acitivty
	 *            The activity which is currently displayed
	 */
	public void suspend(Activity acitivty) {
		// Add a disconnect for the location provider

	}

	// ********************************
	// Switch Activity Functions
	// ********************************

	/**
	 * Switch from a current activity in the application to the settings
	 * activity
	 */
	public void goToSettings() {
		Intent switchToSettings = new Intent(this.parentActivity,
				ListView.class);
		this.parentActivity.startActivity(switchToSettings);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Change the current view to the Restaurant view
	 * 
	 * @param currentList
	 *            The list of places which will be shown
	 */
	public void goToList(List<Place> currentList) {
		this.parentActivity.startActivity((new Intent(this.parentActivity,
				ListItems.class)));
		this.Rest_Places = currentList;

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************
	/**
	 * Call the user's primary browser, open on given url
	 * 
	 * @param url
	 *            The url of the website the browser should open on
	 */
	public void openBrowser(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		this.parentActivity.startActivity(intent);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Transfer given latitude and longitude to google maps
	 * 
	 * @param latitude
	 *            The latitude of the location
	 * @param longitude
	 *            The longitude of the location
	 */
	public static void showMap(String latitude, String longitude,
			Activity parent) {
		Uri current = Uri.parse("http://maps.google.com/maps?saddr=" + latitude
				+ "," + longitude + "&daddr=20.5666,45.345");

		showMap(current, parent);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Transfer the address to the map function
	 * 
	 * @param geoLocation
	 *            The location as a uri
	 */
	private static void showMap(Uri geoLocation, Activity parentActivity) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(geoLocation);
		if (intent.resolveActivity(parentActivity.getPackageManager()) != null) {
			parentActivity.startActivity(intent);
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Go to the url given by the view
	 * @param v
	 */
	public static void goToUrl(View v){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(((TextView) v).getText().toString()));
		v.getContext().startActivity(intent);
	}
	
	// ********************************
	// Settings Functions
	// ********************************

	/**
	 * Write current settings to the system, given the list order
	 * {@value #BOOLEAN_LIST} for the booleans and {@value #STRING_LIST} for the
	 * strings.
	 * 
	 * @param booleanOptions
	 * @param stringOptions
	 */
	public void setSettings(String[] stringOptions) {

		// Number of settings need to be greater than or equal to the number
		if (stringOptions.length >= Control.STRING_LIST_VALUES.length - 1) {
			for (int i = 0; i < Control.STRING_LIST_VALUES.length; i++) {
				this.settingsEditor.putString(Control.STRING_LIST_VALUES[i],
						stringOptions[i]);
			}
		}

		// Push the settings to the setting keep.
		if(this.settingsEditor.commit()){
			Log.d("Truth Wins",this.settings.contains(Control.GET_LIST_AT_STARTUP) ? "true" : "false");
		}else{
			Log.d("Liar Wins",this.settings.contains(Control.GET_LIST_AT_STARTUP) ? "true" : "false");
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************
	/**
	 * Set the settings to default
	 * 
	 * @param override
	 *            Override current setting to default
	 */
	public void setSettingDefaults(boolean override) {
		if (this.settings != null) {
			if (override
					|| !this.settings.contains(Control.GET_LIST_AT_STARTUP)) {
				if (DEBUG) {
					this.setSettings(new String[] { "false", "false", "5",
							"false" });
				} else {
					this.setSettings(this.defaults);
				}
			}
		}
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

		// ***********************************
		// Constructors
		// ***********************************

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

		// ***********************************
		// Functions
		// ***********************************

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// Get Progress bar
			onStartAsync(parentActivity);
			super.onPreExecute();
		}

		// **********************************************************************************************************
		// ----------------------------------------------------------------------------------------------------------
		// **********************************************************************************************************

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

		// **********************************************************************************************************
		// ----------------------------------------------------------------------------------------------------------
		// **********************************************************************************************************

		@Override
		protected void onPostExecute(List<Address> result) {
			Control.onStopAsync(parentActivity);
			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				// this.callback[i].execute(result);
			}
		}

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

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

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Get the user's current Location as a Location object
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

			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				this.callback[i].execute(result);
			}
			// Disconnect from the gps
			currentMapsAPI.disconnect();
			onStopAsync(parentActivity);
		}

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Async retrieve a list of resteraunts, return an intent
	 */
	private class getList extends AsyncTask<String, Void, ArrayList<Place>> {

		private RestListAct[] activityList;
		private String[] opt;

		public getList(RestListAct... params) {
			activityList = params;
		}

		public getList(String[] options, RestListAct... params) {
			opt = options;
			activityList = params;
		}

		@Override
		protected void onPreExecute() {
			onStartAsync(parentActivity);
			super.onPreExecute();
		}

		@Override
		protected ArrayList<Place> doInBackground(String... params) {
			try {

				ArrayList<Place> currentPlaces = null;

				// If nothing passed, then get address, else more places
				if (params[0] == Control.GET_MORE) {
					currentPlaces = places.getMorePlaces();
				} else if (params[0] == Control.NO_ADDRESS) {
					try {
						Location current = gMaps.getCurrentGPSCoordinates();
						opt[0] = String.valueOf(current.getLatitude());
						opt[1] = String.valueOf(current.getLongitude());
						currentPlaces = places.getPlaces(opt);
					} catch (NoGPSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					List<Address> value = getAddress.getLocation(params[0]);
					opt[0] = "" + value.get(0).getLatitude();
					opt[1] = "" + value.get(0).getLongitude();
					currentPlaces = places.getPlaces(opt);

				}

				return currentPlaces;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Place> temp) {
			// Log.d("test",temp.get(0).toString());
			for (int i = 0; i < activityList.length; i++) {
				this.activityList[i].execute(temp);
			}
			onStopAsync(parentActivity);
		}

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Async retrieve new places, return nothing
	 */
	private class GetDetails extends AsyncTask<Place, Void, Place> {

		@Override
		protected void onPreExecute() {
			Control.onStartAsync(parentActivity);
			super.onPreExecute();
		}

		@Override
		protected Place doInBackground(Place... params) {
			places.getDetails(params[0]);
			return null;
		}

		protected void onPostExecute(Place vell) {
			Control.onStopAsync(parentActivity);
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Greenberg Class to Asyncronously get the a list of
	 *         Locations from a string address
	 */
	private class getAddressLocation extends
			AsyncTask<String, Void, List<Address>> {

		AddressList[] callbackList;

		public getAddressLocation(AddressList... list) {
			callbackList = list;
		}

		@Override
		protected List<Address> doInBackground(String... params) {
			// Get Address from list
			try {
				return getAddress.getLocation(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Address> list) {
			// Go through all and execute
			for (int i = 0; i < callbackList.length; i++) {
				callbackList[i].execute(list);
			}
		}

	}

	// **************************
	// Public Interfaces
	// **************************

	/**
	 * 
	 * @author David Interface to utilize a list of restaurants
	 */
	public interface RestListAct {
		public void execute(Place places);

		public void execute(ArrayList<Place> places);

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Greenberg version 1.0.0 Interface containing the execute
	 *         function
	 */
	public interface AddressList {
		public void execute(List<Address> list);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	public static boolean checkForGPS(Context context) {
		return context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_LOCATION_GPS);
	}

	// **************************
	// Static Functions
	// **************************

	/**
	 * Start the spinner spinning
	 * 
	 * @param parentActivity
	 *            The activity on which the bar is to be displayed
	 */
	public static void onStartAsync(Activity parentActivity) {
		((ProgressBar) parentActivity.findViewById(R.id.spinnerProgress))
				.setVisibility(View.VISIBLE);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Stop the spinner from spinning
	 * 
	 * @param parentActivity
	 *            The activity on which the bar is displayed
	 */
	public static void onStopAsync(Activity parentActivity) {
		((ProgressBar) parentActivity.findViewById(R.id.spinnerProgress))
				.setVisibility(View.GONE);
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

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

	// **************************
	// Getters And Setters
	// **************************

	/**
	 * Get the current context
	 * 
	 * @return The current parent context
	 */
	public Activity getContext() {
		return this.parentActivity;
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Set the context to a new context
	 * 
	 * @param context
	 *            The current Context
	 */
	public Control setContext(Activity context) {
		this.parentActivity = context;
		// Create the MapsAPI if needed
		if (gMaps == null) {
			gMaps = new MapsAPI(context);
		} else {
			gMaps.setContext(context);
		}

		// Create the LocationToAddresss if needed
		if (getAddress == null) {
			getAddress = new LocationToAddress(context);
		} else {
			getAddress.setContext(context);
		}

		// Get the settings if not loaded
		if (settings == null) {
			settings = parentActivity.getPreferences(Activity.MODE_PRIVATE);
			// Create the settings editor if it does not exist
			if (this.settingsEditor == null) {
				this.settingsEditor = settings.edit();
			}
		}
		this.setSettingDefaults(false);
		// Create the placesAPI
		String preload = this.getStringSetting(PRELOAD);
		if (DEBUG) {
			places = new PlacesAPI(0);
		} else if (preload != null && preload.compareTo("false") == 0) {
			if (this.places == null) {
				places = new PlacesAPI(0);
			} else {
				places.setPreload(0);
			}
		} else {
			if (this.places == null) {
				places = new PlacesAPI();
			} else {
				places.setPreload(3);
			}
		}

		return this;

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Get a boolean setting from the system
	 * 
	 * @param key
	 *            The setting to recieve. Must be one of these from the list
	 *            {@value #BOOLEAN_LIST}
	 * @return The value of the setting if it exists, null otherwise
	 */
	public Boolean getBooleanSetting(String key) {
		if (settings != null) {
			return settings.getBoolean(key, false);
		} else {
			return null;
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Get a String
	 * 
	 * @param key
	 *            The setting to recieve. Must be one of these from the list
	 *            {@value #STRING_LIST}
	 * @return The value of the setting if it exists, null otherwise
	 */
	public String getStringSetting(String key) {
		if (settings != null) {
			return settings.getString(key, null);
		} else {
			return null;
		}
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	public List<Place> getRestList() {
		return this.Rest_Places;

	}
}
