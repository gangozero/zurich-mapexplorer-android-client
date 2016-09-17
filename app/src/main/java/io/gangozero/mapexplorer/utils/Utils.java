package io.gangozero.mapexplorer.utils;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.RestLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class Utils {

	public static OpenedZone locToOpenedZone(LatLng location) {
		return new OpenedZone(new LatLng(location.latitude, location.longitude));
	}

	public static List<OpenedZone> restLocationsToZones(List<RestLocation> zoneLocations) {
		List<OpenedZone> result = new ArrayList<>();
		for (RestLocation restLocation : zoneLocations) {
			restLocation.lat += 0.000001d;
			restLocation.lon += 0.000001d;
			result.add(new OpenedZone(new LatLng(restLocation.lat, restLocation.lon)));
		}

		return result;
	}

}
