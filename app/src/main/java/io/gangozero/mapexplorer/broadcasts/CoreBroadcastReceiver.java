package io.gangozero.mapexplorer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import io.gangozero.mapexplorer.managers.LocationManager;

import javax.inject.Inject;

/**
 * Created by eleven on 17/09/2016.
 */
public class CoreBroadcastReceiver extends BroadcastReceiver {


	public static final int ACTION_101 = 101;
	public static final int ACTION_102 = 102;

	@Inject LocationManager locationManager;

	@Override public void onReceive(Context context, Intent intent) {

	}
}
