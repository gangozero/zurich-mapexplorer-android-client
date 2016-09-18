package io.gangozero.mapexplorer.managers;

import android.support.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

/**
 * Created by eleven on 17/09/2016.
 */
public interface LocManager {

	boolean isDefaultLoc();

	@Nullable LatLng getCurrentLocationAsync();

	/**
	 * Hot observable
	 */
	Observable<LatLng> getCurrentLocationObservable();

	void enableLoc();

	void postExternalLocation(LatLng latLng);
}
