package placesAPI;

import java.util.Calendar;

import android.location.Location;

/**
 * Holds basic and detailed information about a particular place, as well as 
 *   methods to obtain information like time until closing.
 * 
 * @author Candace Walden
 * @version 0.0.3
 */
public class Place {
	//Basic information about the place
	private String name;		//name of the restaurant
	private Double lat;			//latitude of it's location
	private Double lon;			//longitude of it's location
	private String placeid;		//Google Places id
	private String icon;		//url of icon image
	private float dist;			//distance from given location
	
	private boolean detailed;	//has detailed information been added
	
	//detailed information about the place
	private String address;		//nicely formatted address
	private String phone;		//nicely formatted local phone number
	private String website;		//restaurant website
	private int rating;			//rating on a 10-point scale
	private int price;			//price on a 5-point scale
	private StoreHours[] hours;	//hours of the business
	
	/**
	 * Create a place object with the basic information
	 * @param placeid		Google Places id
	 * @param name			Name of the restaurant
	 * @param latitude		Latitude of it's location
	 * @param longitude		Longitude of it's location
	 * @param icon			URL of icon image
	 */
	public Place(String placeid, String name, Double latitude, Double longitude, String icon)
	{
		this.name = name;
		this.placeid = placeid;
		this.lat = latitude;
		this.lon = longitude;
		this.icon = icon;
		
		this.detailed = false;
	}
	
	/**
	 * Adds the detailed information and sets detailed the flag
	 * @param address	Nicely formatted address
	 * @param phone		Nicely formatted local phone number
	 * @param website	Restaurant website
	 * @param rating	Rating on a 5 point scale
	 * @param price		Price on a 5-point scale
	 */
	public void addDetails(String address, String phone, String website, float rating, int price)
	{
		this.address = address;
		this.phone = phone;
		this.website = website;
		this.rating = (int) Math.round(2*rating);
		this.price = price;
		
		this.detailed = true;
	}
	
	public void setDistance(String currLatitude, String currLongitude)
	{
		double currLat = Double.parseDouble(currLatitude);
		double currLon = Double.parseDouble(currLongitude);
		Location currLoc = new Location("");
		Location loc = new Location("");
		currLoc.setLatitude(currLat);
		currLoc.setLongitude(currLon);
		loc.setLatitude(lat);
		loc.setLongitude(lon);
		
		dist = loc.distanceTo(currLoc);
	}
	
	/**
	 * Get the time until closing in a simple string format.
	 * 	If more than hour, in half hour increments : 1.5h
	 * 	If less than hour, in five minute increments : 35m
	 * @return Time until closing.
	 */
	public String timeUntilClose()
	{
		int today=0; //TODO get today
		int i;
		int time;
		int day;
		
		//get our current time and day
		for(i=0; i<hours.length; i++)
		{
			if(hours[1].getOpenDay() == today)
				break;
		}
		
		time = hours[i].getCloseTime();
		day = hours[i].getCloseDay();
		return "15m";
	}
	
	/**
	 * Method used for testing. Don't mind me.
	 */
	public String toString()
	{
		return name;
	}
	
	/**
	 * @return nicely formatted address
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * @return if this place has details
	 */
	public boolean isDetailed() {
		return this.detailed;
	}

	/**
	 * @return the distance from current searched location
	 */
	public float getDistance() {
		return this.dist;
	}
	
	/**
	 * @param the store hours
	 */
	public void setHours(StoreHours[] hours)
	{
		this.hours = hours;
	}
	
	/**
	 * @return the URL of an icon image
	 */
	public String getIcon() {
		return this.icon;
	}
	
	/**
	 * @return the latitude of the location
	 */
	public Double getLat() {
		return this.lat;
	}
	
	/**
	 * @return the longitude of the location
	 */
	public Double getLong() {
		return this.lon;
	}
	
	/**
	 * @return the name of the restaurant
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return nicely formatted local phone number
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * @return the Google Place's placeID
	 */
	public String getID()
	{
		return this.placeid;
	}
	
	/**
	 * @return the price range on a 5-point scale
	 */
	public int getPrice() {
		return this.price;
	}
	
	/**
	 * @return the rating on a 10-point scale
	 */
	public int getRating(){
		return this.rating;
	}
	
	/**
	 * @return the website URL
	 */
	public String getWebsite(){
		return this.website;
	}
	
	/**
	 * @return hours statement for the current day
	 */
	public String getHours(){
		Calendar calendar = Calendar.getInstance(); 
		int i;
		int today = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		for(i=0; i < hours.length; i++)
		{
			if(hours[i].getOpenDay() == today)
			{
				break;		//found it!
			}
		}
		
		return hours[i].toString();
	}

}
