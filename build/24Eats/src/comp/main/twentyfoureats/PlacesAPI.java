package comp.main.twentyfoureats;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesAPI {
	private static final String API_KEY = "SOEMTJHGOSJG";
	private static final String PLACES_BASE = "https://maps.googleapis.com/maps/api/place/";
	private static final String NEARBY = "nearbysearch/json?";
	
	private ArrayList<Place> places;
	
	public PlacesAPI()
	{
		
	}
	
	public void setUpPlaces(String latitude, String longitude)
	{

		StringBuilder urlSB = new StringBuilder(PLACES_BASE+NEARBY);
		urlSB.append("key="+API_KEY);
		urlSB.append("&location="+latitude+","+longitude);
		urlSB.append("&radius=16000");
		urlSB.append("&types=bar|cafe|food|restaurant");
		urlSB.append("&opennow=true");

		try {
			URL url = new URL(urlSB.toString());
			
			readRestaurantList(getJSON(url));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
		
		return json;
	}
	
	private void readRestaurantList(StringBuilder jsonString)
	{
		String placeid, name, latitude, longitude, icon;
		
		try {
			JSONObject json = new JSONObject(jsonString.toString());
			JSONArray results = json.getJSONArray("results");
			
			for(int i = 0; i<results.length(); i++)
			{
				JSONObject place = results.getJSONObject(i);
				JSONObject location = place.getJSONObject("geometry");
				location = location.getJSONObject("location");
				
				placeid = place.getString("place_id");
				name = place.getString("name");
				latitude = location.getString("lat");
				longitude = location.getString("lon");
				icon = place.getString("icon");
				
				places.add(new Place(placeid, name, latitude, longitude, icon));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
