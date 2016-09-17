package io.gangozero.mapexplorer.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public interface LocationManager {
	LatLng getCurrentLocationAsync();

	Observable<LatLng> getCurrentLocation();

	void startMockLocation0();

	void startMockLocation1();

	void startMockLocation2();

	Observable<LatLng> getCurrentLocationObservable();
}
