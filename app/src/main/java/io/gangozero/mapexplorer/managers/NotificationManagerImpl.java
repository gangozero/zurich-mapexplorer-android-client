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
	private int xpId;

	public NotificationManagerImpl(App app) {
		this.app = app;
	}

	@Override public void showMockMovementStart() {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(app)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("MapExplorer")
				.addAction(getAction(CoreBroadcastReceiver.ACTION_100, "loc:0"))
				.addAction(getAction(CoreBroadcastReceiver.ACTION_101, "loc:1"))
				.addAction(getAction(CoreBroadcastReceiver.ACTION_102, "loc:2"))
				.setOngoing(true)
				.setPriority(NotificationCompat.PRIORITY_MAX)
				.setAutoCancel(false);

		android.app.NotificationManager notificationManager = (android.app.NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, mBuilder.build());
	}

	@Override public void handleXp(float xp) {
		if (xp > 0) {
			xpId++;
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(app)
					.setSmallIcon(R.drawable.ic_cake_white_48dp)
					.setContentTitle("New experience: " + xp)
					.setPriority(NotificationCompat.PRIORITY_MAX)
					.setAutoCancel(false);

			android.app.NotificationManager notificationManager = (android.app.NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.notify(xpId, mBuilder.build());
		}
	}

	private NotificationCompat.Action getAction(int action, String name) {
		Intent intent = new Intent(app, CoreBroadcastReceiver.class);
		intent.putExtra("actionId", action);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(app, action, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		return new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, name, pendingIntent).build();
	}
}