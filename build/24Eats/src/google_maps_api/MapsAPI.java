package google_maps_api;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;

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
	private final LocationManager manager;

	// *********************
	// Constructors
	// ********************

	/**
	 * Create a new instation of the MapsAPI object
	 * 
	 * @param activity
	 *            The activity currently being used
	 */
	public MapsAPI(Activity activity) {
		gps = new GeoLoc(activity);
		getAddress = new LocationToAddress(activity);
		parentActivity = activity;
		this.manager = (LocationManager) activity.getSystemService( activity.LOCATION_SERVICE );
	}

	// *********************
	// Functions
	// ********************

	/**
	 * Find the current location of a user as longitude and latitude
	 * 
	 * @param activity
	 *            The current activity being seen
	 * 
	 * @throws NoGPSException
	 *             Can't get locations
	 */
	public Location getCurrentGPSCoordinates() throws NoGPSException {
		
		Location temp = null;
		gps.connect();
		while (temp == null) {
			temp = gps.getCurrentLoc();
		}
		return temp;
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Find the current address of a user based on gps location
	 * 
	 * @param activity
	 *            The current user activity
	 * @param modifer
	 *            Callback function which occurs after the address has been
	 *            found. It will be recieve a string.
	 * @return The current location as an address
	 * @throws NoGPSException
	 *             Throws when no connection to GPS
	 */
	public String getCurrentAddress() throws NoGPSException {
		// Get the address and return
		return getAddress.getLocation(getCurrentGPSCoordinates());

	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/*
	 * Disconnect the current gps system
	 */
	public void disconnect() {
		this.gps.disconnect();
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

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * 
	 * @author David Interface containing the execute function which will run
	 *         when the asyncronous task is finished
	 */
	public interface LocationCallback {
		public void execute(Location loc);
	}

	/**
	 * Change the context
	 * @param context The context to change to
	 */
	public void setContext(Activity context) {
		this.parentActivity = context;
		
	}
	
	public boolean gpsOn(){
		if (this.manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	       return true;
	    }
		return false;
	}
	
	

}
