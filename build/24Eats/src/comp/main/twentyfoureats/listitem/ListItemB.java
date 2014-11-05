package comp.main.twentyfoureats.listitem;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

//import comp.main.twentyfoureats.OnClickListener;
import comp.main.twentyfoureats.R;
//import comp.main.twentyfoureats.RelativeLayout;

public class ListItemB extends View {
	public int textColor;
	public boolean open;
	public String hours;
	
	public ListItemB(Context context, AttributeSet attrs){
		super(context, attrs);
		//TypedArray a = context.getTheme().obtainStyledAttributes( attrs,R.styleable.ListItem,0, 0);

		   try {
			   //textColor = a.getColor(R.styleable.ListItem_labelColor, 0xFF03007b);
		       //open = a.getBoolean(R.styleable.ListItem_open, true);
		   } finally {
		      // a.recycle();
		   }

		
	}
	
	/*protected void onListItemClick( View v, int position, long id) {
		//super.onListItemClick(v, position, id);
		boolean visible = false;

		ListItem onClick(View v) {
			
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
	}*/

}
