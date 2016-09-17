package io.gangozero.mapexplorer.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

/**
 * Created by eleven on 17/09/2016.
 */
public interface LocationManager {

	boolean isDefaultLoc();

	LatLng getCurrentLocationAsync();

	Observable<LatLng> getCurrentLocation();

	/**
	 * Hot observable
	 */
	Observable<LatLng> getCurrentLocationObservable();

	void enableLoc();

	void disableManager();

	void postExternalLocation(LatLng latLng);
}
