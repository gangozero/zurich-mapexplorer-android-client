package io.gangozero.mapexplorer.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class MockLocationManagerImpl implements LocationManager {
	@Override public Observable<LatLng> getCurrentLocation() {
		return Observable.create(new Observable.OnSubscribe<LatLng>() {
			@Override public void call(Subscriber<? super LatLng> subscriber) {
				subscriber.onNext(new LatLng(47.36766423537108 + 0.001d, 8.526935577392578));
				subscriber.onCompleted();
			}
		});
	}

	@Override public Observable<List<LatLng>> getTouchPoints() {
		return Observable.create(subscriber -> {
			List<LatLng> result = new ArrayList<>();
			double initLat = 47.36766423537108;
			double initLon = 8.526935577392578;
			result.add(new LatLng(initLat, initLon));
			result.add(new LatLng(initLat + 0.001d, initLon + 0.0015d));
			result.add(new LatLng(initLat + 0.002d, initLon + 0.00157d));

			subscriber.onNext(result);
			subscriber.onCompleted();
		});
	}
}
