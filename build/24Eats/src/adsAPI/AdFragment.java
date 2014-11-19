package adsAPI;

import android.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView; 

public class AdFragment extends Fragment {
	private AdView myAdView;
	
	public AdFragment() {}
	
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		myAdView = (AdView) getView().getViewByID(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		myAdView.loadAd(adRequest);}
	
	public View onCreateView(	LayoutInflater inflater, 
									ViewGroup container, 
									Bundle savedInstanceState) {
				return inflater.inflate(R.layout.fragment_ad, container, false);}
		
	//OVERRIDE, Ad will resume status if app is paused in any way
	public void onResume() {
	    super.onResume();
	    if (myAdView != null)
	      myAdView.resume();}
		
	//OVERRIDE, Ad will pause itself when app is not in current use
	public void onPause() {
	    if (myAdView != null)
	      myAdView.pause();
	    super.onPause();}

	//OVERRIDE, Ad will terminate once app is terminated
	public void onDestroy() {
	    if (myAdView != null)
	      myAdView.destroy();
	    super.onDestroy();}
}