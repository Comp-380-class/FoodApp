package applic;

import android.app.Application;
import control.Control;

public class GlobalApplication extends Application {
	public Control mainControl;
	
	@Override
	public void onCreate (){
		mainControl = new Control();
		super.onCreate();
	}
}
