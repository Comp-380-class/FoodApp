package placesAPI;

/**
 * Holds basic and detailed information about a particular place, as well as 
 *   methods to obtain information like time until closing.
 * 
 * @author Candace Walden
 * @version 0.0.2
 */
public class Place {
	public String name;
	public Double lat;
	public Double lon;
	public String placeid;
	public String icon;
	
	public boolean detailed;
	public String address;
	public String phone;
	public String website;
	public int rating;
	public int price;
	
	public Place(String placeid, String name, Double latitude, Double longitude, String icon)
	{
		this.name = name;
		this.placeid = placeid;
		this.lat = latitude;
		this.lon = longitude;
		this.icon = icon;
		this.detailed = false;
	}
	
	public void addDetails(String address, String phone, String website, float rating, int price)
	{
		this.address = address;
		this.phone = phone;
		this.website = website;
		this.rating = (int) Math.round(2*rating);
		this.price = price;
		this.detailed = true;
	}
	
	public String timeUntilClose()
	{
		//if more than hour do half hour increments : 1.5h
		//if less than hour do five minute increments : 40m
		
		return "15m";
	}
	
	public String toString()
	{
		return name;
	}

}
