package io.gangozero.mapexplorer.views;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.models.OpenedZone;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public interface ExplorerMapView {
	void showZones(List<OpenedZone> openedZones);

	void showCurrentLocation(LatLng location);
}