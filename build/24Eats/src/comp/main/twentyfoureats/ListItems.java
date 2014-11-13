package comp.main.twentyfoureats;


import java.util.ArrayList;
import java.util.List;

import placesAPI.Place;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ListItems extends ActionBarActivity {
	//MySimpleArrayAdapter adapter;
	boolean visible;
	//ArrayList<Place> list;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        String[] values = new String[] { "Restaurant1", "Big Restuarnat", "Burger Joint",
            "Fast Food", "Indian", "German", "Breakfast Joint","Restaurant1", "Big Restuarnat", 
            "Burger Joint","Fast Food", "Indian", "German", "Breakfast Joint" };
        
        double[] num = new double[] { 0.5, 0.6, 0.7,
                1.0, 1.2, 2.2, 2.2,2.3, 2.5, 
                2.6,2.7, 2.8, 2.9, 3.0 };
        
       final SparseArray<Place> list = new SparseArray<Place>();
        for (int i = 0; i < values.length; ++i) {
        	String id = String.valueOf(i);
          Place item = new Place(id,values[i],num[i],num[i],"url");
          list.append(i,item);
        }
        

        ExpandListAdapter adapter = new ExpandListAdapter(this,list);
        listView.setAdapter(adapter);
        visible = false;

        

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


