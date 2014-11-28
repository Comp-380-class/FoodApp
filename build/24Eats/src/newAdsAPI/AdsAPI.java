package newAdsAPI;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import comp.main.twentyfoureats.R;

public class AdsAPI {
	private AdView mAdView;
	private Activity parent;

	// ********************************
	// Constructors
	// ********************************
	/**
	 * Create an add
	 * 
	 * @param activity
	 *            The activity in which the ad is meant to be viewed
	 */
	public AdsAPI(Activity activity) {
		this.parent = activity;
		//createAd();
	}

	// ********************************
	// Functions
	// ********************************
	/**
	 * Create the add
	 */
	private void createAd() {
		mAdView = (AdView) parent.findViewById(R.id.adView);
		mAdView.setAdListener(new ToastAdListener(this.parent));
		mAdView.loadAd(new AdRequest.Builder().build());
	}

	
	
	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Pause the ad
	 */
	public void pauseAd() {
		mAdView.pause();
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * Resume the ad
	 */
	public void resumeAd() {
		this.mAdView.resume();
	}

	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************

	/**
	 * destroy the add
	 */
	public void destroyAd() {
		mAdView.destroy();
	}
	
	// **********************************************************************************************************
	// ----------------------------------------------------------------------------------------------------------
	// **********************************************************************************************************
	
	// Do not call this function from the main thread. Otherwise, 
	// an IllegalStateException will be thrown.
	private void getIdThread() {

	  Info adInfo = null;
	  try {
	    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.parent);

	  } catch (IOException e) {
	    // Unrecoverable error connecting to Google Play services (e.g.,
	    // the old version of the service doesn't support getting AdvertisingId).
	 
	  } catch (GooglePlayServicesNotAvailableException e) {
	    // Google Play services is not available entirely.
	  } catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (GooglePlayServicesRepairableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  final String id = adInfo.getId();
	  final boolean isLAT = adInfo.isLimitAdTrackingEnabled();
	}



	/**
	 * Set the activity
	 * 
	 * @param activity
	 *            The new activity
	 */
	public void setActivity(Activity activity) {
		this.parent = activity;
		createAd();
	}
	private class ToastAdListener extends AdListener {
	    private Context mContext;

	    public ToastAdListener(Context context) {
	        this.mContext = context;
	    }

	    @Override
	    public void onAdLoaded() {
	        Toast.makeText(mContext, "onAdLoaded()", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onAdFailedToLoad(int errorCode) {
	        String errorReason = "";
	        switch(errorCode) {
	            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
	                errorReason = "Internal error";
	                break;
	            case AdRequest.ERROR_CODE_INVALID_REQUEST:
	                errorReason = "Invalid request";
	                break;
	            case AdRequest.ERROR_CODE_NETWORK_ERROR:
	                errorReason = "Network Error";
	                break;
	            case AdRequest.ERROR_CODE_NO_FILL:
	                errorReason = "No fill";
	                break;
	        }
	        Toast.makeText(mContext, String.format("onAdFailedToLoad(%s)", errorReason),
	                Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onAdOpened() {
	        Toast.makeText(mContext, "onAdOpened()", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onAdClosed() {
	        Toast.makeText(mContext, "onAdClosed()", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onAdLeftApplication() {
	        Toast.makeText(mContext, "onAdLeftApplication()", Toast.LENGTH_SHORT).show();
	    }
	}

}
