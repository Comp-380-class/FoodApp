package comp.main.twentyfoureats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class OverScrolledListView extends ExpandableListView {
	
	@SuppressLint("NewApi")
	public OverScrolledListView(Context context) {
		super(context);
	}

	public OverScrolledListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public OverScrolledListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	@Override
	  protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
	                  boolean clampedY) {
				Toast.makeText(this.getContext(), "scrollX:" + scrollX + " scrollY:" + scrollY + " clampedX:"
	                          + clampedX + " clampedY:" + clampedX, Toast.LENGTH_LONG).show();
	          
	          
	          super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

	  }
	
}
