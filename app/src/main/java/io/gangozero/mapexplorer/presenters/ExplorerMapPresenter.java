package io.gangozero.mapexplorer.presenters;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.managers.NotificationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.PostLocationBody;
import io.gangozero.mapexplorer.models.PutPointResponse;
import io.gangozero.mapexplorer.models.RestLocation;
import io.gangozero.mapexplorer.utils.Utils;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

	public void onViewCreated(ExplorerMapView view) {
		this.view = view;
		loadData();
		locationManager
				.getCurrentLocationObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(this::handleNewLocation);
	}

	private void handleNewLocation(LatLng location) {

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

		restManager.api()
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
					if (response.xp > 0) {
						notificationManager.handleXp(response.xp);
						openedZones.add(new OpenedZone(location));
						view.updateZones(openedZones);
					}
					view.updateCurrentLocation(location);
				}, Throwable::printStackTrace);
	}

	public void loadData() {

		view.showLoading();

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

		locationManager
				.getCurrentLocation()
				.flatMap(Observable::just)
				.flatMap(new Func1<LatLng, Observable<PutPointResponse>>() {
					@Override public Observable<PutPointResponse> call(LatLng location) {

						return restManager.api().postLocation(
								new PostLocationBody(userId, userToken, location.latitude, location.longitude, System.currentTimeMillis())
						);
					}
				})
				.flatMap(new Func1<PutPointResponse, Observable<List<RestLocation>>>() {
					@Override public Observable<List<RestLocation>> call(PutPointResponse response) {

						notificationManager.handleXp(response.xp);

						return restManager.api().getAllMap(userId, userToken, userId);
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(
						restLocations -> {
							view.updateCurrentLocation(locationManager.getCurrentLocationAsync());
							openedZones = Utils.restLocationsToZones(restLocations);
							view.updateZones(openedZones);
						},
						view::showErrorLoading
				);
	}
}
