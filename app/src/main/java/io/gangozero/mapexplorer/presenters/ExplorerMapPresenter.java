package io.gangozero.mapexplorer.presenters;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.views.ExplorerMapView;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class ExplorerMapPresenter {

	public LocationManager locationManager;

	private ExplorerMapView view;

	public void onViewCreated(ExplorerMapView view) {
		this.view = view;

		locationManager.getCurrentLocation()
				.subscribe(location -> {
					view.showCurrentLocation(location);
					List<OpenedZone> zones = new ArrayList<>();
					zones.add(new OpenedZone(location));
					//view.showZones(zones);
				});

		locationManager.getTouchPoints()
				.subscribe(new Action1<List<LatLng>>() {
					@Override public void call(List<LatLng> points) {
						List<OpenedZone> zones = new ArrayList<>();
						for (LatLng point : points) zones.add(new OpenedZone(point));
						view.showZones(zones);
					}
				});
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
