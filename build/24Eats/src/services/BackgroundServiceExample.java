package services;

import comp.main.twentyfoureats.Form;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.appcompat.R;
import android.util.Log;
import android.widget.Toast;

public class BackgroundServiceExample extends IntentService{
	private int value;
	
	public BackgroundServiceExample(){
		super(".BackgroundServiceExample");
	}
	
	/**
	 * Create a new background service
	 * @param name : The name of the service
	 */
	public BackgroundServiceExample(String name) {
		super(name);
		value = 0;
		// TODO Auto-generated constructor stub
		Intent localIntent =
	            new Intent(Constants2.BROADCAST_ACTION)
	            // Puts the status into the Intent
	            .putExtra(Constants2.EXTENDED_DATA_STATUS, value);
	    // Broadcasts the Intent to receivers in this app.
	    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

		// Create the notification displayed while service is running
		Notification backgroundNotification = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.abc_ic_clear_holo_light)
				.setContentTitle("My notification").setContentText("working!")
				.build();

		// Lock the service to notification bar
		this.startForeground(6, backgroundNotification);

		return super.onStartCommand(intent, flags, startId);
	}
	
	public void onDestroy() {
		Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.abc_textfield_search_selected_holo_dark)
				.setContentTitle("Continued").setContentText("Continued");

		NotificationManager mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		//WifiManager currentManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		//WifiInfo currentNetwork = currentManager.getConnectionInfo();

		/*do {
			if (currentNetwork.getSSID().compareTo("WiredSSID") == 0) {
				Intent intent2 = new Intent(this,Form.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent2);
			}
		}while(currentNetwork.getSSID().compareTo("WiredSSID") == 0);*/

		int incr;
		// Do the "lengthy" operation 20 times
		for (incr = 0; incr <= 100; incr += 5) {
			// Sets the progress indicator to a max value, the
			// current completion percentage, and "determinate"
			// state
			mBuilder.setProgress(100, incr, false);
			mBuilder.setContentText("" + incr);
			// Displays the progress bar for the first time.
			mNotificationManager.notify(6, mBuilder.build());
			// Sleeps the thread, simulating an operation
			// that takes time
			try {
				// Sleep for 5 seconds
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				Log.d(Context.NOTIFICATION_SERVICE, "sleep failure");
			}
		}
		// When the loop is finished, updates the notification
		mBuilder.setContentText("Download complete")
		// Removes the progress bar
				.setProgress(0, 0, false);
		mNotificationManager.notify(5, mBuilder.build());

		// Remove lock on notification bar
		this.stopForeground(false);
		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.
		
		 long endTime = System.currentTimeMillis() + 5 * 1000; while
		 (System.currentTimeMillis() < endTime) { synchronized (this) { try {
		  wait(endTime - System.currentTimeMillis()); } catch (Exception e) { }
		  } }
		 
		value++;
	}
	

	
	
}
