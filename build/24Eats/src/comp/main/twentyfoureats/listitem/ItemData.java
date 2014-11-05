package comp.main.twentyfoureats.listitem;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ItemData {
	List<ListItem> itemList;
	public ItemData(){
		List<ListItem> itemList = new ArrayList<ListItem>();
	}
	//public List<ListItem> findAll(String name, String distance, Activity active){
	public List<ListItem> findAll(String name, String distance){
		List<ListItem> itemList = new ArrayList<ListItem>();
		ListItem item = ListItem.getNew(name, distance);
		itemList.add(item);
		return itemList;
	}

}
