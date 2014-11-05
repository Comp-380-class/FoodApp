package comp.main.twentyfoureats.listitem;

import comp.main.twentyfoureats.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class ListViewSingle extends View {
	private String mName = "";
	private String mDistance = "";
	private float mTextY = 0.0f;
    private float mTextHeight = 0.0f;
    private int mTextColor = 0;
	

	public ListViewSingle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ListViewSingle(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        TypedArray a = context.getTheme().obtainStyledAttributes(
		        attrs,
		        R.styleable.ListView,
		        0, 0);
        try {
            // Retrieve the values from the TypedArray and store into
            // fields of this class.
            //
            // The R.styleable.PieChart_* constants represent the index for
            // each custom attribute in the R.styleable.PieChart array.
            //mName = a.get(R.styleable.ListView_name, "");
            mTextY = a.getDimension(R.styleable.ListView_labelY, 0.0f);
            //mDistance = a.get(R.styleable.ListView_distance, "");
            mTextHeight = a.getDimension(R.styleable.ListView_labelHeight, 0.0f);
            //mTextPos = a.getInteger(R.styleable.ListView_labelPosition, 0);
            mTextColor = a.getColor(R.styleable.ListView_labelColor, 0xff000000);
            //mHighlightStrength = a.getFloat(R.styleable.ListView_highlightStrength, 1.0f);
            //mPieRotation = a.getInt(R.styleable.PieChart_pieRotation, 0);
            //mPointerRadius = a.getDimension(R.styleable.PieChart_pointerRadius, 2.0f);
            //mAutoCenterInSlice = a.getBoolean(R.styleable.PieChart_autoCenterPointerInSlice, false);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }
        init();
	}
	
	public void init() {
		//setContentView(R.layout.activity_list_view);
		
	}



}
