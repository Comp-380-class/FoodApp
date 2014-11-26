package placesAPI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Candace Walden
 * @version 0.0.3
 * 
 * Object connects to and requests information from Google Places API.
 * Parses the information and stores in Place objects.
 */
public class PlacesAPI{
	private static final String API_KEY = "AIzaSyBQoyR-YZVqVujbDaNKZvAK0v09UM4cCLs";
	private static final String PLACES_BASE = "https://maps.googleapis.com/maps/api/place/";
	private static final String NEARBY = "nearbysearch/json?";
	private static final String DETAILS = "details/json?";
	private static final int MIN_TO_METER = 320;
	private static final int MILE_TO_METER = 1609;
	
	private ArrayList<Place> places;
	private String morePlacesToken;
	private String[] options;
	private int preload = 3;
	
	public PlacesAPI()
	{
		this.places = new ArrayList<Place>(); 
	}
	
	/** 
	 * @param preload The number of places to preload.
	 */
	public PlacesAPI(int preload)
	{
		this.places = new ArrayList<Place>();
		
		//Can't preload more results than possible
		if(preload < 20)
			this.preload = preload;
		else
			this.preload = 20;
	}
	
	/**
	 * Get the first twenty places that match the search criteria.
	 * 
	 * @param options	[0] => latitude
	 * 					[1] => longitude
	 * 					[2] => distance
	 * 					[3] => minutes
	 * 	If both are set, distance is used, if neither is set, rank by distance is used
	 * @return			The first twenty Place objects matching the request or null if no places found
	 */
	public ArrayList<Place> getPlaces(String[] options)
	{
		this.options = options;		//save options if we want to modify them
		
		//build request
		StringBuilder urlSB = new StringBuilder(PLACES_BASE+NEARBY);
		urlSB.append("key="+API_KEY);
		urlSB.append("&location="+options[0]+","+options[1]);
		if(options.length >=3 && options[2] != null)
		{		
			urlSB.append("&radius="+(Double.parseDouble(options[2])*MILE_TO_METER));
		}
		else if(options.length >=4 && options[3] != null)
		{
			urlSB.append("&radius="+(Integer.parseInt(options[3])*MIN_TO_METER));
		}
		else
		{
			urlSB.append("&rankby=distance");
		}
		urlSB.append("&types=bar|cafe|restaurant");
		urlSB.append("&opennow=true");

		try {
			URL url = new URL(urlSB.toString());
			
			this.places = readRestaurantList(getJSON(url));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		preloadPlaces();
		
		if(this.places.isEmpty())
		{
			places.add(new Place());
		}
			
		return this.places;
	}
	
	/**
	 * Get the next 20 places from the previous search. If the previous 
	 *   search did not have more results to return, this will return null.
	 *   
	 * There must be a delay between this search and the last search request
	 * 
	 * Only up to 60 places may be returned via this method.
	 *   
	 * @return Next twenty Places or null if no places returned.
	 */
	public ArrayList<Place> getMorePlaces()
	{
		if(morePlacesToken == "")		//there is no more places
			return null;
		
		ArrayList<Place> updated = new ArrayList<Place>();
		StringBuilder urlSB = new StringBuilder(PLACES_BASE+NEARBY);
		urlSB.append("key="+API_KEY);
		urlSB.append("&pagetoken="+morePlacesToken);
		
		URL url;
		try {
			url = new URL(urlSB.toString());
			updated = readRestaurantList(getJSON(url));
			
			this.places.addAll(updated);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(updated.isEmpty())
		{
			updated.add(new Place());
		}
			
		return updated;
	}
	
	/**
	 * Get the details for the particular place. They will be stored in
	 *   the place object given.
	 * @param place		The place object which needs details.
	 */
	public void getDetails(Place place)
	{
		if(!place.isDetailed()){
			StringBuilder urlSB = new StringBuilder(PLACES_BASE+DETAILS);
			urlSB.append("key="+API_KEY);
			urlSB.append("&placeid="+place.getID());
			
			URL url;
			
			try {
				url = new URL(urlSB.toString());
				
				readDetails(getJSON(url), place);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPreload(int number)
	{
		//Can't preload more results than possible
		if(number < 20)
			this.preload = number;
		else
			this.preload = 20;
	}

	private StringBuilder getJSON(URL url)
	{
		HttpURLConnection conn = null;
	    StringBuilder json = new StringBuilder();

		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			
			int read;
			char[] buffer = new char[1024];
			while ((read = in.read(buffer)) != -1) {
	            json.append(buffer, 0, read);
	        }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
		
		return json;
	}
	
	private ArrayList<Place> readRestaurantList(StringBuilder jsonString)
	{
		String placeid, name, icon;
		Double latitude, longitude;
		ArrayList<Place> placeList = new ArrayList<Place>();
		
		try {
			JSONObject json = new JSONObject(jsonString.toString());
			JSONArray results = json.getJSONArray("results");
			
			if(json.has("next_page_token"))
			{
				morePlacesToken = json.getString("next_page_token");
			}
			else
			{
				morePlacesToken = "";
			}
			
			for(int i = 0; i<results.length(); i++)
			{
				JSONObject place = results.getJSONObject(i);
				JSONObject location = place.getJSONObject("geometry");
				location = location.getJSONObject("location");
				
				//if any of these don't exist, we have a problem
				placeid = place.getString("place_id");
				name = place.getString("name");
				latitude = location.getDouble("lat");
				longitude = location.getDouble("lng");
				
				//allow icon to be blank
				if(place.has("icon"))
					icon = place.getString("icon");
				else
					icon = "";
				
				placeList.add(new Place(placeid, name, latitude, longitude, icon));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return placeList;
	}
	
	private void readDetails(StringBuilder json, Place place) 
	{
		String address, phone, website, openHours, closeHours;
		float rating;
		int price, openDay, closeDay;
		StoreHours[] storeHours;
		
		try {
			JSONObject details = new JSONObject(json.toString());
			details = details.getJSONObject("result");
			
			//get address
			if(details.has("formatted_address")) 
				address = details.getString("formatted_address");
			else
				address = "";
			
			//get phone number
			if(details.has("formatted_phone_number")) 
				phone = details.getString("formatted_phone_number");
			else
				phone = "";
			
			//get website
			if(details.has("website")) 
				website = details.getString("website");
			else
				website = "";
			
			//get rating
			if(details.has("rating")) 
				rating = (float) details.getDouble("rating");
			else
				rating = 0;
			
			//get price
			if(details.has("price_level")) 
				price = details.getInt("price_level");
			else
				price = 0;
			
			place.addDetails(address, phone, website, rating, price);
			
			place.setDistance(options[0],options[1]);
			
			JSONObject openHrs = details.getJSONObject("opening_hours");
			JSONArray hours = openHrs.getJSONArray("periods");
			
			storeHours = new StoreHours[hours.length()];
			for(int i=0; i<hours.length(); i++)
			{
				JSONObject hour = hours.getJSONObject(i);
				JSONObject open = hour.getJSONObject("open");
				if(hour.has("close"))
				{
					JSONObject close = hour.getJSONObject("close");
					
					closeHours = close.getString("time");
					closeDay = close.getInt("day");
					openHours = open.getString("time");
					openDay = open.getInt("day");
					
					storeHours[i] = new StoreHours(openDay, openHours, closeDay, closeHours);
				}
				else
				{
					place.setAlwaysOpen();
					break;
				}
			}
			
			place.setHours(storeHours);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void preloadPlaces()
	{
		int load;
		
		if(preload > places.size())
		{
			load = places.size();
		}
		else
		{
			load = preload;
		}
		
		for(int i=0; i<load; i++)
		{
			getDetails(places.get(i));
		}
	}
}
