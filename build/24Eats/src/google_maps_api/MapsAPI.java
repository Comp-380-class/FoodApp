package google_maps_api;

import android.app.Activity;
import android.location.Location;

/**
 * 
 * @author David Greenberg
 * Top level class for controlling the entire GPS System
 */
public class MapsAPI {
	Addresses getAddress;
	GoogleMaps gps;
	
	/**
	 * Find the current location of a user as longitude and latitude
	 * @param activity The current activity being seen
	 * @return The current location, in gps, as a location object
	 */
	public Location getCurrentGPSCoordinates(Activity activity){
		return null;
	}
	
	/**
	 * Find the current address of a user
	 * @param activity The current user activity
	 * @return The current address as a string
	 */
	public String getCurrentAddress(Activity activity){
		return null;
	}
	
	/**
	 * Find the current address of a user
	 * @param activity The current user activity
	 * @param modifer Callback function which occurs after the address has been found. It will be recieve a string.
	 */
	public void getCurrentAddress(Activity activity,AddressesModifier modifier){
		
	}
	
	
}
