package io.gangozero.mapexplorer.presenters;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.PutPointResponse;
import io.gangozero.mapexplorer.models.RestLocation;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class ExplorerMapPresenter {

	@Inject public LocationManager locationManager;
	@Inject public RestManager restManager;

	private ExplorerMapView view;

	public void onViewCreated(ExplorerMapView view) {
		this.view = view;

		locationManager
				.getCurrentLocation()
				.map(latLng -> {
					view.showCurrentLocation(latLng);
					return latLng;
				})
				.flatMap(new Func1<LatLng, Observable<PutPointResponse>>() {
					@Override public Observable<PutPointResponse> call(LatLng location) {
						return restManager.api().putPoint(location.latitude, location.longitude);
					}
				})
				.flatMap(new Func1<PutPointResponse, Observable<List<RestLocation>>>() {
					@Override public Observable<List<RestLocation>> call(PutPointResponse putPointResponse) {
						return restManager.api().getAllMap("", "");
					}
				})
				.subscribe(new Action1<List<RestLocation>>() {
					@Override public void call(List<RestLocation> zoneLocations) {
						view.showZones(restLocationsToZones(zoneLocations));
					}
				});

//		locationManager.getTouchPoints()
//				.subscribe(points -> {
//					List<OpenedZone> zones = new ArrayList<>();
//					for (LatLng point : points) zones.add(new OpenedZone(point));
//					view.showZones(zones);
//				});
	}

	private List<OpenedZone> restLocationsToZones(List<RestLocation> zoneLocations) {
		List<OpenedZone> result = new ArrayList<>();
		for (RestLocation location : zoneLocations)
			result.add(new OpenedZone(new LatLng(location.lat, location.lon)));

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
