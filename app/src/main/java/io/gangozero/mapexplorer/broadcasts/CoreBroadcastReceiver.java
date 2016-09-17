package io.gangozero.mapexplorer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.managers.LocationManager;

import javax.inject.Inject;

/**
 * Created by eleven on 17/09/2016.
 */
public class CoreBroadcastReceiver extends BroadcastReceiver {

	public static final int ACTION_100 = 100;
	public static final int ACTION_101 = 101;
	public static final int ACTION_102 = 102;

	@Inject LocationManager locationManager;

	@Override public void onReceive(Context context, Intent intent) {
		int actionId = intent.getExtras().getInt("actionId");

		DIHelper.coreComponent().inject(this);

		if (actionId == ACTION_100) {
			locationManager.startMockLocation0();
		} else if (actionId == ACTION_101) {
			locationManager.startMockLocation1();
		} else if (actionId == ACTION_102) {
			locationManager.startMockLocation2();
		}
	}
}
