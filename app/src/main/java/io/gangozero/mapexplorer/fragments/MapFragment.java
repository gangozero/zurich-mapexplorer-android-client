package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 16/09/2016.
 */
public class MapFragment extends BaseMapFragment {

	public static MapFragment create() {
		return new MapFragment();
	}

	@Override protected void onMapCreated() {
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);
		polygonOptions.strokeColor(0);

		double initLat = 47.36766423537108;
		double initLon = 8.526935577392578;

		polygonOptions.add(new LatLng(initLat, initLon));
		polygonOptions.add(new LatLng(initLat + 10, initLon));
		polygonOptions.add(new LatLng(initLat + 10, initLon + 10));
		polygonOptions.add(new LatLng(initLat, initLon + 10));
		polygonOptions.add(new LatLng(initLat, initLon));

		List<LatLng> hole = new ArrayList<>();

		hole.add(new LatLng(2, 2));
		hole.add(new LatLng(4, 2));
		hole.add(new LatLng(4, 4));
		hole.add(new LatLng(2, 4));
		hole.add(new LatLng(2, 2));

		map.addPolygon(polygonOptions);
		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.zoom(16);
		builder.target(new LatLng(initLat, initLon));

		map.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}
}
