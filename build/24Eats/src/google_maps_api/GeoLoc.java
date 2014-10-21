package google_maps_api;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import constants.ConstantValues;

public class GeoLoc
		implements
		OnConnectionFailedListener,
		LocationListener, com.google.android.gms.location.LocationListener, ConnectionCallbacks {

	// *****************************
	// * Private Variables
	// ****************************
	private Activity parentActivity;
	private GoogleApiClient locClient;
	private LocationRequest locReq;
	private Location curLoc;
	//********************************
	// Final Variables
	//********************************
	private final long REQUEST_UPDATE_TIME = 5000;
	private final float REQUEST_MIN_DISTANCE = 0;

	// *****************************
	// * Constructors
	// ****************************

	/**
	 * Create a new GeoLoc object
	 * 
	 * @param activity
	 *            The activity in which the geolocation will be performed in
	 */
	public GeoLoc(Activity activity) {
		this.parentActivity = activity;
		//this.locClient = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		locClient = new GoogleApiClient.Builder(activity).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
		
	}

	// *****************************
	// * Inherited Functions
	// ****************************

	@Override
	public void onLocationChanged(Location location) {
		curLoc = location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnected(Bundle arg0) {
		// Create request for location
		this.locReq = LocationRequest.create();
		locReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(REQUEST_UPDATE_TIME).setFastestInterval(REQUEST_UPDATE_TIME);
		LocationServices.FusedLocationApi.requestLocationUpdates(this.locClient, this.locReq, this);
	}

	

	// *****************************
	// * New Functions
	// ****************************

	/**
	 * Connect to the gps system
	 */
	public void connect(){
		//Connect to the GPS services
		this.locClient.blockingConnect();
	}
	
	/**
	 * Get the currentLocation from the client
	 * @return The current location, if it exists, null otherwise
	 */
	public Location getCurrentLoc(){
		return this.curLoc;
	}
	
	/**
	 * Disconnect from the gps system
	 */
	public void disconnect(){
		this.curLoc = null;
		this.locClient.disconnect();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
