package io.gangozero.mapexplorer.presenters;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.LocManager;
import io.gangozero.mapexplorer.managers.NotificationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.Poi;
import io.gangozero.mapexplorer.models.PostLocationBody;
import io.gangozero.mapexplorer.utils.Utils;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class ExplorerMapPresenter {

	@Inject public LocManager locManager;
	@Inject public RestManager restManager;
	@Inject public KeyValueManager keyValueManager;
	@Inject public NotificationManager notificationManager;

	private ExplorerMapView view;
	private List<OpenedZone> openedZones;
	private CompositeSubscription sub;
	private static final String TAG = "pres";

	public void onViewDestroyed() {
		view = null;
		if (sub != null) sub.unsubscribe();
	}

	public void onViewCreated(ExplorerMapView view) {

		Log.i(TAG, "onViewCreated");

		sub = new CompositeSubscription();
		this.view = view;

		sub.add(locManager
				.getCurrentLocationObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::handleNewLocation, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						throwable.printStackTrace();
					}
				}));

		locManager.enableLoc();
	}

	private void handleNewLocation(LatLng location) {

		Log.i(TAG, "location:" + location.toString());

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

		sub.add(restManager.api()
				.postLocation(new PostLocationBody(
						userId,
						userToken,
						location.latitude,
						location.longitude,
						System.currentTimeMillis()
				))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(response -> {
					if (response.xp_square > 0) {
						notificationManager.handleXp(response.xp_square + response.xp_poi);
						openedZones.add(new OpenedZone(location));
						view.updateZones(openedZones);
					}
					view.updateCurrentLocation(location);
				}, Throwable::printStackTrace));

		loadZones(location);
		loadPoi(location);
	}

	private void loadPoi(LatLng currentLoc) {

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);
		//LatLng currentLoc = locManager.getCurrentLocationAsync();

		sub.add(restManager
				.api()
				.getPoi(
						userToken,
						userId,
						currentLoc.latitude,
						currentLoc.longitude
				)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<List<Poi>>() {
					@Override public void call(List<Poi> response) {
						view.updatePoi(response);
					}
				}, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						throwable.printStackTrace();
					}
				}));
	}

	public void loadZones(LatLng location) {

		view.showLoading();

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);


		sub.add(restManager.api().getMap(userId, userToken, userId)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(
						restLocations -> {
							view.updateCurrentLocation(location);
							openedZones = Utils.restLocationsToZones(restLocations);
							view.updateZones(openedZones);
						},
						view::showErrorLoading
				));
	}
}
