package io.gangozero.mapexplorer.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import io.gangozero.mapexplorer.App;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.broadcasts.CoreBroadcastReceiver;

/**
 * Created by eleven on 17/09/2016.
 */
public class NotificationManagerImpl implements NotificationManager {

	private App app;

	public NotificationManagerImpl(App app) {
		this.app = app;
	}

	@Override public void showMockMovementStart() {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(app)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("MapExplorer")
				.addAction(getActionFirst())
				.addAction(getActionSecond())
				.setOngoing(true)
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setAutoCancel(false);

		android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}

	private NotificationCompat.Action getActionFirst() {
		Intent intent = new Intent(app, CoreBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(app, CoreBroadcastReceiver.ACTION_101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "mov(1)", pendingIntent).build();
	}


	private NotificationCompat.Action getActionSecond() {
		Intent intent = new Intent(app, CoreBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(app, CoreBroadcastReceiver.ACTION_102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "mov(2)", pendingIntent).build();
	}
}