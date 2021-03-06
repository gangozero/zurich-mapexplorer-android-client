package io.gangozero.mapexplorer.views;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.Poi;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public interface ExplorerMapView {

	void updateZones(List<OpenedZone> openedZones);

	void showLoading();

	void updateCurrentLocation(LatLng location);

	void showErrorLoading(Throwable t);

	void updatePoi(List<Poi> pois);
}