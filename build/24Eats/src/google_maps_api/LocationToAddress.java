package google_maps_api;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

/**
 * A subclass of AsyncTask that calls getFromLocation() in the background. The
 * class definition has these generic types: Location - A Location object
 * containing the current location. Void - indicates that progress units are not
 * used String - An address passed to onPostExecute()
 */
public class LocationToAddress extends AsyncTask<Location, Void, String> {
	private Context mContext;
	private AddressCallback callback;
	/**
	 * Create the addresses object
	 * 
	 * @param context
	 *            The context of the current activity
	 */
	public LocationToAddress(Context context) {
		super();
		mContext = context;
	}
	
	/**
	 * Constructor
	 * 
	 * @param context
	 *            The current context of the object
	 * @param func
	 *            The object containing the function to be run after the results
	 *            have been received
	 */
	public LocationToAddress(Context context, AddressCallback func) {
		this(context);
		callback = func;
	}

	/**
	 * A method that's called once doInBackground() completes. Turn off the
	 * indeterminate activity indicator and set the text of the UI element that
	 * shows the address. If the lookup failed, display the error message.
	 */
	@Override
	protected void onPostExecute(String address) {
		if (callback != null) {
			callback.execute(address);
		}
	}

	/**
	 * Get a Geocoder instance, get the latitude and longitude look up the
	 * address, and return it
	 * 
	 * @params params One or more Location objects
	 * @return A string containing the address of the current location, or an
	 *         empty string if no address can be found, or an error message
	 */
	@Override
	protected String doInBackground(Location... params) {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		// Get the current location from the input parameter list
		Location loc = params[0];
		// Create a list to contain the result address
		List<Address> addresses = null;
		try {
			/*
			 * Return 1 address.
			 */
			addresses = geocoder.getFromLocation(loc.getLatitude(),
					loc.getLongitude(), 1);
		} catch (IOException e1) {
			Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
			e1.printStackTrace();
			return ("IO Exception trying to get address");
		} catch (IllegalArgumentException e2) {
			// Error message to post in the log
			String errorString = "Illegal arguments "
					+ Double.toString(loc.getLatitude()) + " , "
					+ Double.toString(loc.getLongitude())
					+ " passed to address service";
			Log.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			return errorString;
		}
		// If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {
			// Get the first address
			Address address = addresses.get(0);
			/*
			 * Format the first line of address (if available), city, and
			 * country name.
			 */
			String addressText = String.format(
					"%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName());
			// Return the text
			return addressText;
		} else {
			return "No address found";
		}
	}
	
	public String getLocation(Location...params){
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		// Get the current location from the input parameter list
		Location loc = params[0];
		// Create a list to contain the result address
		List<Address> addresses = null;
		try {
			/*
			 * Return 1 address.
			 */
			addresses = geocoder.getFromLocation(loc.getLatitude(),
					loc.getLongitude(), 1);
		} catch (IOException e1) {
			Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
			e1.printStackTrace();
			return ("IO Exception trying to get address");
		} catch (IllegalArgumentException e2) {
			// Error message to post in the log
			String errorString = "Illegal arguments "
					+ Double.toString(loc.getLatitude()) + " , "
					+ Double.toString(loc.getLongitude())
					+ " passed to address service";
			Log.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			return errorString;
		}
		// If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {
			// Get the first address
			Address address = addresses.get(0);
			/*
			 * Format the first line of address (if available), city, and
			 * country name.
			 */
			String addressText = String.format(
					"%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address
							.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName());
			// Return the text
			return addressText;
		} else {
			return "No address found";
		}
	}
	
	/**
	 * Turn a String Address into a location object
	 * @param address A list of possible gps locations
	 * @return The address as a pair of gps locations
	 */
	public List<Address> getGPSLocation(String address){
		Geocoder geocoder = new Geocoder(mContext);  
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocationName(address, 1);
			if(addresses.size() > 0) {
			    return addresses;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null; 
		}
		
		return null;
	}
	
	//****************************
	//Getters and Setters
	//****************************
	public void setCallback(AddressCallback callback){
		this.callback = callback;
	}

}
