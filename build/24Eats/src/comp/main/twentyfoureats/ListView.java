package comp.main.twentyfoureats;

import placesAPI.Place;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import applic.GlobalApplication;
import control.Control;

public class ListView extends ActionBarActivity {

	private Control mainControl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		Intent intent = getIntent();
		this.mainControl = ((GlobalApplication)getApplication()).mainControl.setContext(this);
		Place[] currentList = (Place[]) intent.getExtras().get(Control.REST_LIST);
		fillInList(currentList[0].getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void fillInList(CharSequence values){
		Button listOne = (Button) findViewById(R.id.list_button);
		listOne.setText(values);
	}
	
}
