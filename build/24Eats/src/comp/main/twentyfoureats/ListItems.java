package comp.main.twentyfoureats;

//import comp.main.twentyfoureats;

//import android.content.Intent;


//import comp.main.twentyfoureats.listitem.ListItem;

//import comp.main.twentyfoureats.listitem.ItemData;
import comp.main.twentyfoureats.listitem.ListItem;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
//import android.widget.TextView;
import java.util.List;

public class ListItems extends Activity {
	//private ItemData datasource;
	List<ListItem> data;
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		//listView=(ListView) findViewById(R.id.list);
		data = new ArrayList<ListItem>();
		//datasource =  new ItemData();
		//List<ListItem> items = datasource.findAll("Restaurant",".5m");
		ListItem item = ListItem.getNew("Restaurant", ".5m");
		data.add(item);
		item = ListItem.getNew("Restaurant2", ".6m");
		data.add(item);
		item = ListItem.getNew("Restaurant3", ".8m");
		data.add(item);
		
/*		ArrayAdapter<ListItem> adapter = new ArrayAdapter<ListItem>(this,
	              android.R.layout.simple_list_item_1, data);
		 listView.setAdapter(adapter);*/
		//refresh();
		/*item = data.get(1);
		Log.i("List",item.getName());
		Log.i("ListD",item.getDistance());*/
		
		/*ListItem test = new ListItem("The Beast",true,"5pm-11pm");
		ListItem test2 = new ListItem("The Beast",true,"5pm-11pm");
		ListItem test3 = new ListItem("The Beast",true,"5pm-11pm");
		
		ListItem[] items = new ListItem[] {test,test2,test3};
		for (int i=0;i<items.length;i++){
			
		
		TextView theText = (TextView) findViewById(R.id.name);
		theText.setText(test.name);
		
		theText = (TextView) findViewById(R.id.hours);
		theText.setText(test.hours);
		
		theText = (TextView) findViewById(R.id.open);
		if(test.open){
			theText.setText("open");
		} else {
			theText.setText("closed");
		}
		View panel = findViewById(R.id.panel);
		panel.setVisibility(View.GONE);
		View btnView = findViewById(R.id.btnPanel);
		btnView.setId(i);
		
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnView.getLayoutParams();
		if(i> 0){
			int priorView = i-1;
			//View changeView = findViewById(priorView);
			//int prior = R.id.priorView;
			params.addRule(RelativeLayout.BELOW, priorView);
		}
			

		}*/
		//View theButton = findViewById(R.id.btnPanel);
		
		
		View btnProfile = findViewById(R.id.btnPanelProfile);
		View btnSettings = findViewById(R.id.btnPanelSettings);
		View btnPrivacy = findViewById(R.id.btnPanelPrivacy);
		

		View panelProfile = findViewById(R.id.panelProfile);
		panelProfile.setVisibility(View.GONE);

		View panelSettings = findViewById(R.id.panelSettings);
		panelSettings.setVisibility(View.GONE);

		View panelPrivacy = findViewById(R.id.panelPrivacy);
		panelPrivacy.setVisibility(View.GONE);
		
		
		btnProfile.setOnClickListener(new OnClickListener() {
			boolean visible = false;
			@Override
			public void onClick(View v) {
				
				// DO STUFF
				View panelProfile = findViewById(R.id.panelProfile);
				if (visible){
					panelProfile.setVisibility(View.GONE);
					visible=false;
				}else{
					View btnView = findViewById(R.id.btnPanelSettings);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnView.getLayoutParams();
					params.addRule(RelativeLayout.BELOW, R.id.panelProfile);
					
					panelProfile.setVisibility(View.VISIBLE);
					visible=true;
				}

				View panelSettings = findViewById(R.id.panelSettings);
				panelSettings.setVisibility(View.GONE);

				View panelPrivacy = findViewById(R.id.panelPrivacy);
				panelPrivacy.setVisibility(View.GONE);

			}
		});

		btnSettings.setOnClickListener(new OnClickListener() {
			boolean visible = false;
			@Override
			public void onClick(View v) {
				// DO STUFF
				View panelProfile = findViewById(R.id.panelProfile);
				panelProfile.setVisibility(View.GONE);
				
				View panelSettings = findViewById(R.id.panelSettings);
				if (visible){
					panelSettings.setVisibility(View.GONE);
					visible = false;
				} else{
					View btnView = findViewById(R.id.btnPanelPrivacy);
					RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnView.getLayoutParams();
					params.addRule(RelativeLayout.BELOW, R.id.panelSettings);

					panelSettings.setVisibility(View.VISIBLE);
					visible = true;
				}

				View panelPrivacy = findViewById(R.id.panelPrivacy);
				panelPrivacy.setVisibility(View.GONE);

			}
		});

		btnPrivacy.setOnClickListener(new OnClickListener() {
			boolean visible = false;
			@Override
			public void onClick(View v) {
				// DO STUFF
				View panelProfile = findViewById(R.id.panelProfile);
				panelProfile.setVisibility(View.GONE);

				View panelSettings = findViewById(R.id.panelSettings);
				panelSettings.setVisibility(View.GONE);

				View panelPrivacy = findViewById(R.id.panelPrivacy);
				if (visible){
					panelPrivacy.setVisibility(View.GONE);
					visible = false;
				}else{
					panelPrivacy.setVisibility(View.VISIBLE);
					visible = true;
				}

			}
		});

		//Intent intent = getIntent();
		//CharSequence currentList = intent.getCharSequenceExtra(Form.EXTRA_MESSAGE);
		//fillInList(currentList);
	}

	/*private void refresh() {
		ArrayAdapter<ListItem> adaptor = new ArrayAdapter<ListItem>(this,android.R.layout.simple_list_item_1,data);
		setListAdapter(adaptor);		
	}*/

	private void setListAdapter(ArrayAdapter<ListItem> adapter) {
		// TODO Auto-generated method stub
		
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
		//Button listOne = (Button) findViewById(R.id.list_button);
		//listOne.setText(values);
	}
}
