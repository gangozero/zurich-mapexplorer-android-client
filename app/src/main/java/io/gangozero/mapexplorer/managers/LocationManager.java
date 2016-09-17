package io.gangozero.mapexplorer.managers;

import com.google.android.gms.maps.model.LatLng;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public interface LocationManager {
	Observable<LatLng> getCurrentLocation();

	Observable<List<LatLng>> getTouchPoints();
}
