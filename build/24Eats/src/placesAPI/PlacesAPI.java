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
 * @version 0.0.2
 * 
 * Object connects to and requests information from Google Places API.
 * Parses the information and stores in Place objects.
 */
public class PlacesAPI{
	private static final String API_KEY = "AIzaSyBQoyR-YZVqVujbDaNKZvAK0v09UM4cCLs";
	private static final String PLACES_BASE = "https://maps.googleapis.com/maps/api/place/";
	private static final String NEARBY = "nearbysearch/json?";
	private static final String DETAILS = "details/json?";
	
	private ArrayList<Place> places;
	private String morePlacesToken;
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
	 * @param latitude		The latitude of the location to search
	 * @param longitude		The longitude of the location to search
	 * @return				The first twenty Place objects matching the request
	 */
	public ArrayList<Place> getPlaces(String latitude, String longitude)
	{

		StringBuilder urlSB = new StringBuilder(PLACES_BASE+NEARBY);
		urlSB.append("key="+API_KEY);
		urlSB.append("&location="+latitude+","+longitude);
		urlSB.append("&radius=16000");
		urlSB.append("&types=bar|cafe|restaurant");
		urlSB.append("&opennow=true");

		try {
			URL url = new URL(urlSB.toString());
			
			this.places = readRestaurantList(getJSON(url));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		preloadPlaces();
		
		return this.places;
	}
	
	/**
	 * Get the next 20 places from the previous search. If the previous 
	 *   search did not have more results to return, this will send back 
	 *   an empty ArrayList.
	 *   
	 * @return Next twenty Places.
	 */
	public ArrayList<Place> getMorePlaces()
	{
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
		
		return updated;
	}
	
	/**
	 * Get the details for the particular place. They will be stored in
	 *   the place object given.
	 * @param place		The place object which needs details.
	 */
	public void getDetails(Place place)
	{
		StringBuilder urlSB = new StringBuilder(PLACES_BASE+DETAILS);
		urlSB.append("key="+API_KEY);
		urlSB.append("&placeid="+place.placeid);
		
		URL url;
		
		try {
			url = new URL(urlSB.toString());
			
			readDetails(getJSON(url), place);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder getJSON(URL url)
	{
		HttpURLConnection conn = null;
	    StringBuilder json = new StringBuilder();

		try {
			conn = (HttpURLConnection) url.openConnection();
			
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
				
				placeid = place.getString("place_id");
				name = place.getString("name");
				latitude = location.getDouble("lat");
				longitude = location.getDouble("lng");
				icon = place.getString("icon");
				
				placeList.add(new Place(placeid, name, latitude, longitude, icon));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return placeList;
	}
	
	private void readDetails(StringBuilder json, Place place) 
	{
		String address, phone, website;
		float rating;
		int price;
		
		try {
			JSONObject details = new JSONObject(json.toString());
			details = details.getJSONObject("result");
			
			address = details.getString("formatted_address");
			phone = details.getString("formatted_phone_number");
			website = details.getString("website");
			rating = (float) details.getDouble("rating");
			price = details.getInt("price_level");
			
			place.addDetails(address, phone, website, rating, price);
			
			// TODO Parse hours
			JSONObject open = details.getJSONObject("opening_hours");
			JSONArray hours = open.getJSONArray("periods");
			
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
