package placesAPI;

public class Place {
	public String name;
	public String lat;
	public String lon;
	public String placeid;
	public String icon;
	
	public String address;
	public String phone;
	public String website;
	public int rating;
	public int price;
	
	public Place(String placeid, String name, String latitude, String longitude, String icon)
	{
		this.name = name;
		this.placeid = placeid;
		this.lat = latitude;
		this.lon = longitude;
		this.icon = icon;
	}

}
