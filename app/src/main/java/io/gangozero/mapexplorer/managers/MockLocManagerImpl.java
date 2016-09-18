package io.gangozero.mapexplorer.managers;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by eleven on 17/09/2016.
 */
public class MockLocManagerImpl implements LocManager {

	private PublishSubject<LatLng> locationSubject = PublishSubject.create();

	private LatLng DEFAULT_LOC = new LatLng(47.372649, 8.543755);
	private LatLng currentLocation = DEFAULT_LOC;
	private KeyValueManager keyValueManager;

	public MockLocManagerImpl(KeyValueManager keyValueManager) {
		this.keyValueManager = keyValueManager;
		String lastLoc = keyValueManager.getString(KeyValueManager.LAST_LOC);
		stringToLoc(lastLoc);
	}

	@Override public boolean isDefaultLoc() {
		return DEFAULT_LOC.equals(currentLocation);
	}

	@Override public LatLng getCurrentLocationAsync() {
		return currentLocation;
	}


	private void updateLocationObservable() {
		Log.i("pres", "updateLocationObservable");
		if (currentLocation != null) {
			keyValueManager.putString(KeyValueManager.LAST_LOC, stringToLatLng(currentLocation));
		}
		locationSubject.onNext(currentLocation);
	}

	@Override public void enableLoc() {
		updateLocationObservable();
	}

	@Override public void postExternalLocation(LatLng latLng) {
		currentLocation = latLng;
		updateLocationObservable();
	}

	@Override public Observable<LatLng> getCurrentLocationObservable() {
		return locationSubject.asObservable();
	}

	private void stringToLoc(String string) {
		if (string == null) return;
		String[] values = string.split("-");
		double lat = Double.parseDouble(values[0]);
		double lon = Double.parseDouble(values[1]);
		currentLocation = new LatLng(lat, lon);
	}

	private String stringToLatLng(LatLng latLng) {
		String lat = Double.toString(latLng.latitude);
		String lon = Double.toString(latLng.longitude);
		return lat + "-" + lon;
	}
}
