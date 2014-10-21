package comp.main.twentyfoureats;

public class Place {
	public String name;
	public String lat;
	public String lon;
	public String placeid;
	public String icon;
	
	public Place(String placeid, String name, String latitude, String longitude, String icon)
	{
		this.name = name;
		this.placeid = placeid;
		this.lat = latitude;
		this.lon = longitude;
		this.icon = icon;
	}

}
