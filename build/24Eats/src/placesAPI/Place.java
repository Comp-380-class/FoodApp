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
	private boolean open24 = false;		//if this place is open 24 hours a day
	
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
	 * Junk place for error messages
	 */
	public Place()
	{
		this.name = "No Locations Available";
		this.lat = 0.0;
		this.lon = 0.0;
		this.icon = "http://example.com/example.png";
		this.detailed = true;
		this.address = "N/A";
		this.phone = "N/A";
		this.website = "N/A";
		this.rating = 0;
		this.price = 0;
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
		if(!detailed)
		{
			return "No details available.";
		}
		
		int today= Calendar.DAY_OF_WEEK - 1;
		int currTime = Calendar.HOUR_OF_DAY * 100 + Calendar.MINUTE;
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
		
		if(time<currTime && day == today)
		{
			if(i == 0)		//if first one go to last
			{
				i=hours.length;
			}
			else			//otherwise go one back
			{
				i--;
			}
		}
		
		
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
	 * Set the hours to open 24 hours a day
	 */
	public void setAlwaysOpen()
	{
		this.open24 = true;
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
		if(!detailed)
			return "N/A";
		
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
		if(!detailed)
			return 0;
		
		return this.price;
	}
	
	/**
	 * @return the rating on a 10-point scale
	 */
	public int getRating(){
		if(!detailed)
			return 0;
		
		return this.rating;
	}
	
	/**
	 * @return the website URL
	 */
	public String getWebsite(){
		if(!detailed)
			return "N/A";
		
		return this.website;
	}
	
	/**
	 * @return hours statement for the current day
	 */
	public String getHours(){
		if(!detailed)
			return "N/A";
		
		if(open24)
			return "24Hours";
		
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
