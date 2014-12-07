package comp.main.twentyfoureats;

import java.util.ArrayList;
import java.util.List;

import placesAPI.Place;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import applic.GlobalApplication;
import control.Control;
import control.Control.RestListAct;

public class ListItems extends ActionBarActivity {
	// MySimpleArrayAdapter adapter;
	boolean visible;
	// ArrayList<Place> list;
	Control mainControl;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        ((GlobalApplication) getApplication()).mainControl.setContext(this);
		this.mainControl = ((GlobalApplication) getApplication()).mainControl;
        
		OverScrolledListView listView = (OverScrolledListView) findViewById(R.id.listView);
        
        List<Place> temp = this.mainControl.getRestList();
        
        if(temp!=null){
        
        
	       final SparseArray<Place> list = new SparseArray<Place>();
	       int i=0;
	       for(Place item : temp){
	    	   
	          list.append(i,item);
	          i++;
	        }
        

	        final ExpandListAdapter adapter = new ExpandListAdapter(this,list);
	        listView.setAdapter(adapter);
	        listView.setControl(this.mainControl,adapter);
	        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					Place item = (Place) adapter.getGroup(groupPosition);
					if(adapter.getLastOpen()!=-1){
						parent.collapseGroup(adapter.getLastOpen());
						adapter.setLastOpen(groupPosition);
					}else if(adapter.getLastOpen()==groupPosition){
						adapter.setLastOpen(-1);
					}else{
						adapter.setLastOpen(groupPosition);
					}
					final ExpandableListView parent2 = parent;
					final int groupNum = groupPosition;
					if (!item.isDetailed()) {
						mainControl.getDetails(item,new RestListAct(){

							@Override
							public void execute(Place places) {
								parent2.expandGroup(groupNum);
							}

							@Override
							public void execute(ArrayList<Place> places) {
								// TODO Auto-generated method stub
								
							}
							
						});
						return true;
					}else{
						return false;
					}
				}
			});
	        
	        visible = false;
        }else{
        	Toast.makeText(this, "No Locations Nearby", Toast.LENGTH_LONG).show();
        }
        

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
			this.mainControl.goToSettings();
		}
		return super.onOptionsItemSelected(item);
	}

}
