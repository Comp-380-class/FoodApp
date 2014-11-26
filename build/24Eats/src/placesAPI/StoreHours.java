package placesAPI;

public class StoreHours {
	private int openDay;
	private int openHour;
	private int closeDay;
	private int closeHour;
	
	public StoreHours(int openD, String openH, int closeD, String closeH){
		this.openDay = openD;
		this.closeDay = closeD;
		this.openHour = Integer.parseInt(openH);
		this.closeHour = Integer.parseInt(closeH);
	}
	
	public String toString()
	{
		String hoursStatement;
		
		hoursStatement = timeString(openDay, openHour)+" - "+timeString(closeDay, closeHour); 
		
		return hoursStatement;
	}
	
	private String timeString(int day, int time)
	{
		String timeString;
		int hours, minutes;
	
		hours = time/100;
		minutes = time%100;
		
		if(hours == 0)
		{
			timeString=12+":"+String.format("%02d", minutes)+" AM";
		}
		else if(hours < 12)
		{
			timeString=hours+":"+String.format("%02d", minutes)+" AM";
		}
		else if(hours == 12)
		{
			timeString=hours+":"+String.format("%02d", minutes)+" PM";
		}
		else
		{
			hours -= 12;
			timeString=hours+":"+String.format("%02d", minutes)+" PM";
		}
		
		return timeString;
	}
	
	public int getOpenDay()
	{
		return openDay;
	}
	
	public int getOpenTime()
	{
		return openHour;
	}
	
	public int getCloseDay()
	{
		return closeDay;
	}
	
	public int getCloseTime()
	{
		return closeHour;
	}
	
}
