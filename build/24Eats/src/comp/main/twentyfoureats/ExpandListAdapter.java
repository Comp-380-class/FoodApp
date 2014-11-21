package comp.main.twentyfoureats;

import placesAPI.Place;
import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import applic.GlobalApplication;
import control.Control;
/**
 * 
 * @author Mary Verplank - Formats each listing to be displayed 
 * on a main ListView
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

  private final SparseArray<Place> groups;
  public LayoutInflater inflater;
  public Activity activity;
  private Control mainControl;

  public ExpandListAdapter(Activity act, SparseArray<Place> groups) {
    activity = act;
    this.groups = groups;
    inflater = act.getLayoutInflater();
    this.mainControl = ((GlobalApplication) activity.getApplication()).mainControl;
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
	public View getChildView(int groupPosition, final int childPosition,
		      boolean isLastChild, View convertView, ViewGroup parent) {
    
	  Place item = (Place) getGroup(groupPosition);
    //Place detail = this.mainControl.getListOfResteraunts(context, currentLocation, callback, options)
    //item.addDetails("1412 Campus Rd, Los Angeles, CA 90042", "323-702-8975", "http://www.url.com", 4, 2);
	 TextView text = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.panel_view, null);
    }

    //text = (TextView) convertView.findViewById(R.id.hours);
    //text.setText(item.getHours());
    //text = (TextView) convertView.findViewById(R.id.tilClose);
    //text.setText(item.timeUntilClose());
    text = (TextView) convertView.findViewById(R.id.address);
    text.setText(item.getAddress());
    text = (TextView) convertView.findViewById(R.id.phone);
    text.setText(item.getPhone());
    text = (TextView) convertView.findViewById(R.id.rating);
    text.setText(item.getRating()+"");
    text = (TextView) convertView.findViewById(R.id.price);
    text.setText(item.getPrice()+"");
    text = (TextView) convertView.findViewById(R.id.website);
    text.setText(item.getWebsite());
    convertView.invalidate();
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
	  return 1;
  }

  @Override
  public Object getGroup(int groupPosition) {
    return groups.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return groups.size();
  }

  @Override
  public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

  @Override
  public void onGroupExpanded(int groupPosition) {   
	  
	  Place item = (Place) getGroup(groupPosition);
    
	if(!item.isDetailed()){
    	this.mainControl.getDetails(item);
    }
    
    super.onGroupExpanded(groupPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.button_only_view, null);
    }
    Place group = (Place) getGroup(groupPosition);
    TextView text = (TextView) convertView.findViewById(R.id.name);
    text.setText(group.getName());
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }
} 