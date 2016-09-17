package io.gangozero.mapexplorer.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by eleven on 17/09/2016.
 */
public class MockLocationManagerImpl implements LocationManager {

	private PublishSubject<LatLng> locationSubject = PublishSubject.create();

	private LatLng[] locations = new LatLng[]{
			new LatLng(47.372649, 8.543755),
			new LatLng(47.373019, 8.543765),
			new LatLng(47.374068, 8.543722)
	};

	private LatLng currentLocation = locations[0];

	@Override public LatLng getCurrentLocationAsync() {
		return currentLocation;
	}

	@Override public Observable<LatLng> getCurrentLocation() {
		return Observable.create(subscriber -> {
			subscriber.onNext(currentLocation);
			subscriber.onCompleted();
		});
	}

	@Override public void startMockLocation0() {
		currentLocation = locations[0];
		locationSubject.onNext(locations[0]);
	}

	@Override public void startMockLocation1() {
		currentLocation = locations[1];
		locationSubject.onNext(locations[1]);
	}

	@Override public void startMockLocation2() {
		currentLocation = locations[2];
		locationSubject.onNext(currentLocation);
	}

	@Override public Observable<LatLng> getCurrentLocationObservable() {
		return locationSubject;
	}
}
