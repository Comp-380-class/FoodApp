package comp.main.twentyfoureats;


import java.util.List;

import placesAPI.Place;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;
import applic.GlobalApplication;
import control.Control;

public class ListItems extends ActionBarActivity {
	//MySimpleArrayAdapter adapter;
	boolean visible;
	//ArrayList<Place> list;
	Control mainControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        ((GlobalApplication) getApplication()).mainControl.setContext(this);
		this.mainControl = ((GlobalApplication) getApplication()).mainControl;
        
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
       
        List<Place> temp = this.mainControl.getRestList();
        
        if(temp!=null){
        
        
       final SparseArray<Place> list = new SparseArray<Place>();
       int i=0;
       for(Place item : temp){
    	   
          list.append(i,item);
          i++;
        }
        

        ExpandListAdapter adapter = new ExpandListAdapter(this,list);
        listView.setAdapter(adapter);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
}


