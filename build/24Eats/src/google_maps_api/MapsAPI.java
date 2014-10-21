package google_maps_api;

import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import comp.main.twentyfoureats.R;

/**
 * 
 * @author David Greenberg Top level class for controlling the entire GPS System
 */
public class MapsAPI {

	// *********************
	// Declare Variables
	// ********************
	private LocationToAddress getAddress;
	private GeoLoc gps;
	private Activity parentActivity;

	/**
	 * Create a new instation of the MapsAPI object
	 * 
	 * @param activity
	 *            The activity currently being used
	 */
	public MapsAPI(Activity activity) {
		gps = new GeoLoc(activity);
		// gps.connect();
		getAddress = new LocationToAddress(activity);
		parentActivity = activity;
	}

	/**
	 * Find the current location of a user as longitude and latitude
	 * 
	 * @param activity
	 *            The current activity being seen
	 * 
	 * @throws NoGPSException
	 *             Can't get locations
	 */
	public void getCurrentGPSCoordinates(LocationCallback callback)
			throws NoGPSException {
		(new getNewLocation(callback)).execute(this.gps);
	}

	/**
	 * Find the current address of a user based on gps location
	 * 
	 * @param activity
	 *            The current user activity
	 * @param modifer
	 *            Callback function which occurs after the address has been
	 *            found. It will be recieve a string.
	 */
	public void getCurrentAddress(final AddressCallback modifier) {
		new getNewAddress(modifier).execute(this.gps);
	}

	// ********************************************
	// Public Classes
	// ********************************************

	/**
	 * 
	 * Exceptions class for GPS
	 * 
	 * @author David
	 */
	public class NoGPSException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Create an exceptions with a message
		 * 
		 * @param message
		 *            The message to be thrown
		 */
		public NoGPSException(String message) {
			super(message);
		}
	}

	/**
	 * 
	 * @author David Interface containing the execute function which will run
	 *         when the asyncronous task is finished
	 */
	public interface LocationCallback {
		public void execute(Location loc);
	}

	// ********************************************
	// Private Classes
	// ********************************************

	/**
	 * 
	 * @author David
	 * 
	 */
	private class getNewLocation extends AsyncTask<GeoLoc, Location, Location> {
		private Location temp = null; // Create location to use in return
		private GeoLoc currentGeoLoc;
		private LocationCallback[] callback;

		public getNewLocation(LocationCallback... callback) {
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
		protected Location doInBackground(GeoLoc... params) {
			currentGeoLoc = params[0];
			currentGeoLoc.connect();
			while (temp == null) {
				temp = currentGeoLoc.getCurrentLoc();
			}
			return temp;
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
			currentGeoLoc.disconnect();
		}

	}

	

	/**
	 * 
	 * @author David Get GPS Location from gps and return an address
	 */
	private class getNewAddress extends AsyncTask<GeoLoc, Location, String> {
		private ProgressBar currentProgress;
		private Location temp = null; // Create location to use in return
		private GeoLoc currentGeoLoc;
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
		protected String doInBackground(GeoLoc... params) {
			currentGeoLoc = params[0];

			// Connect to the device
			currentGeoLoc.connect();

			// Wait for update
			while (temp == null) {
				temp = currentGeoLoc.getCurrentLoc();
			}
			// Get the address and return
			return getAddress.getLocation(temp);
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
			currentGeoLoc.disconnect();
		}

	}

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

}
