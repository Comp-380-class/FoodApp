package control;

import google_maps_api.AddressCallback;
import google_maps_api.LocationToAddress;
import google_maps_api.MapsAPI;
import google_maps_api.MapsAPI.LocationCallback;
import google_maps_api.MapsAPI.NoGPSException;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * 
 * @author David Greenberg
 * Class for controlling all other underlying classes
 * 
 */
public class Control {
	private MapsAPI gMaps;
	private LocationToAddress getAddress;
	private Activity parentActivity;
	/**
	 * Primary constructor for the control object. Creates a new Control containing information about the activity.
	 * @param activity The current activity in which the program is operating 
	 */
	public Control(Activity activity){
		this.parentActivity = activity;
		gMaps = new MapsAPI(activity);
		getAddress = new LocationToAddress(activity);
	}
	
	/*
	 * Check to see if the current device has google play services installed
	 * @return True if the software is installed, false otherwise
	 */
	public boolean checkForGooglePlayServices(){
		int return_code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.parentActivity);
		
		if(return_code == ConnectionResult.SUCCESS){
			//Everything is succesful, return true
			return true;
		}else if(return_code == ConnectionResult.SERVICE_MISSING){
			//The service is not installed, recommend installation
			return false;
		}else if(return_code == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){
			//The service is out of date, recommend updating the service
			return false;
		}else if(return_code == ConnectionResult.SERVICE_DISABLED){
			//The service is not connected, throw alert telling them to enable service
			return false;
		}else if(return_code == ConnectionResult.SERVICE_INVALID){
			//Service is invalid, return false
		}
		
		//None of the above match, something else must be wrong
		return false;
	}
	
	/**
	 * Get the current location of the user as a string
	 * @return The user's current location as a string
	 */
	public void getCurrentAddress(AddressCallback callback){
		gMaps.getCurrentAddress(callback);
	}
	
	/*
	 * Get a user's current location as a Location object.
	 * @return The user's current location as a location object
	 */
	public void getCurrentLocation(LocationCallback callback) throws NoGPSException{
			gMaps.getCurrentGPSCoordinates(callback);
	}
	
	/**
	 * Get a location based on a provided address
	 * @param Address The address to be anaylzed
	 * @return The address as a Location object
	 */
	public Location getCurrentLocation(String address){
		return null;
	}
	
	/**
	 * Get a list of Resteraunts in the area. When finished will transfer to the list page of the app
	 * @param context The context in which the object is being accessed from
	 * @param currentLocation The current location of the user as a string
	 */
	public void getListOfResteraunts(Context context,String currentLocation, String ... options){
		//Add functions to get location based on given values
		(new getLocationFromAddress(options,new AddressCallback(){

			@Override
			public void execute(String data) {
				// TODO Auto-generated method stub
				
			}
			
		})).execute(options);
	}
	
	/**
	 * Perform when the application goes to suspend
	 * @param acitivty The activity which is currently displayed
	 */
	public void suspend(Activity acitivty){
		//Add a disconnect for the location provider
		
	}
	
	
	/**
	 * 
	 * @author David
	 * Get a Location object from a class
	 */
	private class getLocationFromAddress extends
			AsyncTask<String, Void, String> {

		

		private AddressCallback[] callback;
		private String[] options;
		public getLocationFromAddress(String[] options,AddressCallback... callback) {
			this.callback = callback;
			this.options = options;
		}
		
		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// Get Progress bar
			MapsAPI.onStartAsync(parentActivity);
			super.onPreExecute();
		}


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Address> temp;
			
			for(int i=0;i<params.length;i++){
				temp = getAddress.getGPSLocation(params[i]);
			}
			return options[0];
			
		}

		@Override
		protected void onPostExecute(String result) {
			MapsAPI.onStopAsync(parentActivity);
			// Execute all the callbacks
			for (int i = 0; i < this.callback.length; i++) {
				this.callback[i].execute(result);
			}
		}

	}
	
}
