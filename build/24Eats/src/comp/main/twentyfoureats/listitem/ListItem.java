package comp.main.twentyfoureats.listitem;

//import android.app.Activity;
/*import android.content.Context;
import android.view.View;
import android.widget.Button;*/

//import comp.main.twentyfoureats.R;

public class ListItem{
	private String name;
	private String distance;
	private String hours;
	private String timeLeft;
	private String address;
	private String phone;
	private String website;
	private float rating;
	private int price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	/*public ListItem(Context context) {
        super(context);
        //init();
    }*/

	//public static ListItem getNew(String name,String distance,Activity theActivity){
	public static ListItem getNew(String name,String distance){
		ListItem item = new ListItem();
		item.setName(name);
		item.setDistance(distance);
		return item;
	
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

	
	/*public ListItem(Activity theActivity){
	
	      theActivity.setContentView(R.layout.item_list);
	      //View theButton = findViewById(R.id.btnPanel);
	
	   }*/
	
	

}
