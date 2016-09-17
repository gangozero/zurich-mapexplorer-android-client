package io.gangozero.mapexplorer.presenters;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.PostLocationBody;
import io.gangozero.mapexplorer.models.PutPointResponse;
import io.gangozero.mapexplorer.models.RestLocation;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class ExplorerMapPresenter {

	@Inject public LocationManager locationManager;
	@Inject public RestManager restManager;
	@Inject public KeyValueManager keyValueManager;

	private ExplorerMapView view;

	public void onViewCreated(ExplorerMapView view) {
		this.view = view;
		loadData();
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
					@Override public Observable<List<RestLocation>> call(PutPointResponse putPointResponse) {
						return restManager.api().getAllMap(userId, userToken, userId);
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(
						new Action1<List<RestLocation>>() {
							@Override public void call(List<RestLocation> restLocations) {

								RestLocation locZero = restLocations.get(0);
								view.showCurrentLocation(new LatLng(locZero.lat, locZero.lon));

								view.showZones(restLocationsToZones(restLocations));
							}
						},
						view::showErrorLoading
				);
	}

	private List<OpenedZone> restLocationsToZones(List<RestLocation> zoneLocations) {
		List<OpenedZone> result = new ArrayList<>();
		for (RestLocation location : zoneLocations) {
			location.lat += 0.000001d;
			location.lon += 0.000001d;
			result.add(new OpenedZone(new LatLng(location.lat, location.lon)));
		}


		return result;
	}


	private OpenedZone openedZoneFromLocation(LatLng location) {
		double lat = location.latitude;
		double lon = location.longitude;

		double d = 10000d;
		lat = (double) Math.round(lat * d) / d;
		lon = (double) Math.round(lon * d) / d;

		double diffLat = 0.0001;
		double diffLon = diffLat * 1.5d;

		return null;

//		return new OpenedZone(
//				new LatLng(lat, lon),
//				new LatLng(lat + diffLat, lon),
//				new LatLng(lat + diffLat, lon + diffLon),
//				new LatLng(lat, lon + diffLon)
//		);
	}
}
