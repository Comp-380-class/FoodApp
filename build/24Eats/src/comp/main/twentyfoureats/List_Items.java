package comp.main.twentyfoureats;


import java.util.List;

import placesAPI.Place;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import applic.GlobalApplication;
import control.Control;

public class List_Items extends ActionBarActivity {
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
        
		OverScrolledListView listView = (OverScrolledListView) findViewById(R.id.listView);
		
        listView.setIndicatorBounds(10, 5);
        List<Place> temp = this.mainControl.getRestList();
        
        
        double[] num = new double[] { 0.5, 0.6, 0.7,
                1.0, 1.2, 2.2, 2.2,2.3, 2.5, 
                2.6,2.7, 2.8, 2.9, 3.0 };
        
       final SparseArray<Place> list = new SparseArray<Place>();
       int i=0;
       for(Place item : temp){
    	   
          list.append(i,item);
          i++;
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


