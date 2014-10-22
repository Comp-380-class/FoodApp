package placesAPI;

public class Place {
	public String name;
	public Double lat;
	public Double lon;
	public String placeid;
	public String icon;
	
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
	}
	
	public String timeRemaining()
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
