package comp.main.twentyfoureats;

import java.util.ArrayList;

import placesAPI.Place;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.Toast;
import applic.GlobalApplication;
import control.Control;
import control.Control.RestListAct;

public class OverScrolledListView extends ExpandableListView {
	private ExpandListAdapter view;
	private Control mainControl;

	@SuppressLint("NewApi")
	public OverScrolledListView(Context context) {
		super(context);
		this.setOverscrollFooter(context.getResources().getDrawable(R.drawable.backgradient));
		this.setOverscrollHeader(context.getResources().getDrawable(R.drawable.backgradient));
	}

	public OverScrolledListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public OverScrolledListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	@SuppressLint("NewApi")
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		this.mainControl.getMoreResteraunts(null, new RestListAct() {

			@Override
			public void execute(Place places) {
				// TODO Auto-generated method stub

			}

			@Override
			public void execute(ArrayList<Place> places) {
				// TODO Auto-generated method stub
				view.addChildren(places);
			}

		});

		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	public void setControl(Control mainControl, ExpandListAdapter adapter) {
		this.mainControl = mainControl;
		this.view = adapter;
	}
}
