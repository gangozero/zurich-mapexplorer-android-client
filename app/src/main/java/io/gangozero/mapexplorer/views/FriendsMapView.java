package io.gangozero.mapexplorer.views;

import io.gangozero.mapexplorer.models.RestLocation;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public interface FriendsMapView {
	void onError(Throwable throwable);

	void showMaps(List<RestLocation> locations);
}
