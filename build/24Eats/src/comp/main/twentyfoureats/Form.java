package comp.main.twentyfoureats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Form extends ActionBarActivity {
	public final static String EXTRA_MESSAGE = "Form.message";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.form, menu);
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
    
    //Switch between current activity and new activity with a given value
    public void switchActivityWithIntent(View test){
    	//CharSequence valueToPass = ((TextView)findViewById(R.id.Location)).getText(); 
    	Intent switchToListAct = new Intent(this,ListItems.class);
    	//switchToListAct.setAction("FILL_LIST");
    	//switchToListAct.putExtra(EXTRA_MESSAGE, valueToPass);
    	startActivity(switchToListAct);
    }
}
