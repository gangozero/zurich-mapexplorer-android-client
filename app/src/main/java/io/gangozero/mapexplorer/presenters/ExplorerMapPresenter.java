package io.gangozero.mapexplorer.presenters;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.managers.NotificationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.*;
import io.gangozero.mapexplorer.utils.Utils;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class ExplorerMapPresenter {

	@Inject public LocationManager locationManager;
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

		sub.add(locationManager
				.getCurrentLocationObservable()
//				.subscribeOn(Schedulers.io())
//				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::handleNewLocation));

		locationManager.enableLoc();
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

		loadZones();
		loadPoi();
	}

	private void loadPoi() {

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);
		LatLng currentLoc = locationManager.getCurrentLocationAsync();

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

	public void loadZones() {

		view.showLoading();

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

//		sub.add(locationManager
//				.getCurrentLocation()
//				.flatMap(Observable::just)
//				.flatMap(new Func1<LatLng, Observable<PutPointResponse>>() {
//					@Override public Observable<PutPointResponse> call(LatLng location) {
//
//						return restManager.api().postLocation(
//								new PostLocationBody(userId, userToken, location.latitude, location.longitude, System.currentTimeMillis())
//						);
//					}
//				})
//				.flatMap(new Func1<PutPointResponse, Observable<List<RestLocation>>>() {
//					@Override public Observable<List<RestLocation>> call(PutPointResponse response) {
//
//						notificationManager.handleXp(response.xp_square + response.xp_poi);
//
//						return restManager.api().getAllMap(userId, userToken, userId);
//					}
//				})
		sub.add(restManager.api().getAllMap(userId, userToken, userId)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(
						restLocations -> {
							view.updateCurrentLocation(locationManager.getCurrentLocationAsync());
							openedZones = Utils.restLocationsToZones(restLocations);
							view.updateZones(openedZones);
						},
						view::showErrorLoading
				));
	}
}
