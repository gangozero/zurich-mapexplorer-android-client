package io.gangozero.mapexplorer.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by eleven on 17/09/2016.
 */
public class RestLocation {

	public double lat;
	public double lon;
	public int heat;

	public static LatLng toLatLng(RestLocation restLocation) {
		return new LatLng(restLocation.lat, restLocation.lon);
	}

	public LatLng toLatLng() {
		return new LatLng(lat, lon);
	}
}