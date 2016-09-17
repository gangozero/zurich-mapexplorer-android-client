package io.gangozero.mapexplorer;

import android.app.Application;
import io.gangozero.mapexplorer.di.DIHelper;

/**
 * Created by eleven on 17/09/2016.
 */
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		DIHelper.init(this);
	}
}
