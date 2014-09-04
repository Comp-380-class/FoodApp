package google_maps_api;




import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

/**
 * 
 * @author David Greenberg
 * @version 0.0.1 Object for getting the location of the user from the google
 *          play services.
 */
public class GoogleMaps implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {

	// Global constants
	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private Location currentLoc;
	private FragmentActivity parentActivity;
	private LocationClient locationClient;
	private boolean locationUpdatesRequested = false;
	private LocationRequest locationRequest;
	// Milliseconds per second
	private static final int MILLISECONDS_PER_SECOND = 1000;
	// Update frequency in seconds
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	// Update frequency in milliseconds
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	// The fastest update frequency, in seconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	/**
	 * Create a service for getting the users current location
	 * 
	 * @param activity
	 *            FragmentActivity currently in use for the app
	 */
	public GoogleMaps(FragmentActivity activity) {
		parentActivity = activity;
		locationClient = new LocationClient(activity, this, this);

	}

	// Define the callback method that receives location updates
	@Override
	public void onLocationChanged(Location location) {
		// Set the currentLocation for use outside object
		this.currentLoc = location;
	}

	/**
	 * Connect to location services
	 */
	public void startLocation() {
		locationClient.connect();
	}

	/**
	 * Disconnect from location services
	 */
	public void stopLocation() {
		locationClient.disconnect();
	}

	/**
	 * Start getting location updates
	 */
	public void startUpdates() {
		if (locationRequest == null) {
			locationRequest = LocationRequest.create();
		}
		// Use high accuracy
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 5 seconds
		locationRequest.setInterval(UPDATE_INTERVAL);
		// Set the fastest update interval to 1 second
		locationRequest.setFastestInterval(FASTEST_INTERVAL);
		if (locationClient.isConnected()) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}
		this.locationUpdatesRequested = true;

	}

	/**
	 * Stop getting updates on the current location
	 */
	public void stopUpdates() {
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
			this.locationUpdatesRequested = false;
		} else {
			this.locationUpdatesRequested = false;
		}
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Toast.makeText(parentActivity, "Connected", Toast.LENGTH_SHORT).show();

		// Asks for location update
		if (this.locationUpdatesRequested) {
			locationClient.requestLocationUpdates(locationRequest, this);
		}

	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(parentActivity,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			Log.d("failure", "" + connectionResult.getErrorCode());

			// showErrorDialog(connectionResult.getErrorCode());
		}

	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onDisconnected() {

		// Display the connection status
		Toast.makeText(parentActivity, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	// *********************************
	// -------Getters And Setters-------
	// *********************************

	// Return current location
	public Location getLocation() {
		return this.currentLoc;
	}

	// Return if the device is currently updating the location
	public boolean isUpdating() {
		return this.locationUpdatesRequested;
	}

	// Return if the location service is started
	public boolean isLocationStarted() {
		return this.locationClient.isConnected();
	}

	

}



