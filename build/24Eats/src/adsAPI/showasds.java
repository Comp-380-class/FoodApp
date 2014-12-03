package adsAPI;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import comp.main.twentyfoureats.R;
//need to find correct way to import AdsAPI

public class showasds {
	private AdView adview;
	private static final String AD_UNIT_ID = "24eats ad banner";
	
	
	//Ad is created on startup with any initial settings here
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		adview = new AdView(this);
		adview.setAdSize(AdSize.Banner);
		adview.setAdUnitID(AD_UNIT_ID);
		
		LinearLayout layout = (LinearLayout) findViewByID(R.id.linear_layout);
		layout.addView(adview);
		
		AdRequest adrequest = new AdRequest.Builder()
			.addTestDevice(AdRequest.Device_ID_EMULATOR)
			.addTestDevice("")
			.build();
		
		adview.loadAd(adrequest);
	}
	
	
	//OVERRIDE, Ad will resume status if app is paused in any way
	public void onResume() {
	    super.onResume();
	    if (adview != null)
	      adview.resume();}
	
	//OVERRIDE, Ad will pause itself when app is not in current use
	public void onPause() {
	    if (adview != null)
	      adview.pause();
	    super.onPause();}

	//OVERRIDE, Ad will terminate once app is terminated
	public void onDestroy() {
	    if (adview != null)
	      adview.destroy();
	    super.onDestroy();}
}
