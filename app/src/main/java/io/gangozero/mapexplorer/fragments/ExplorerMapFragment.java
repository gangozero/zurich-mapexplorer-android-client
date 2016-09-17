package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import io.gangozero.mapexplorer.managers.MockLocationManagerImpl;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.presenters.ExplorerMapPresenter;
import io.gangozero.mapexplorer.views.ExplorerMapView;

import java.util.ArrayList;
import java.util.List;

//import com.mapbox.mapboxsdk.annotations.PolygonOptions;
//import com.mapbox.mapboxsdk.camera.CameraPosition;
//import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
//import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by eleven on 16/09/2016.
 */
public class ExplorerMapFragment extends BaseMapFragment implements ExplorerMapView {

	private ExplorerMapPresenter presenter;

	public static ExplorerMapFragment create() {
		return new ExplorerMapFragment();
	}

	public ExplorerMapFragment() {
		setRetainInstance(true);
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ExplorerMapPresenter();
		presenter.locationManager = new MockLocationManagerImpl();
	}

	@Override protected void onMapCreated() {

		presenter.onViewCreated(this);
		addUnexploredZone();

//		polygonOptions.add(new LatLng(initLat, initLon));
//		polygonOptions.add(new LatLng(initLat + 10, initLon));
//		polygonOptions.add(new LatLng(initLat + 10, initLon + 10));
//		polygonOptions.add(new LatLng(initLat, initLon + 10));
//		polygonOptions.add(new LatLng(initLat, initLon));

//		List<LatLng> hole = new ArrayList<>();

//		hole.add(new LatLng(2, 2));
//		hole.add(new LatLng(4, 2));
//		hole.add(new LatLng(4, 4));
//		hole.add(new LatLng(2, 4));
//		hole.add(new LatLng(2, 2));
//
//		polygonOptions.addHole(hole);

		//map.addPolygon(polygonOptions);
//		CameraPosition.Builder builder = new CameraPosition.Builder();
//		builder.zoom(16);
		//builder.target(new LatLng(initLat, initLon));
//		map.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}

	@Override public void showZones(List<OpenedZone> openedZones) {
		List<CircleOptions> touchPoints = new ArrayList<>();
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);
		//polygonOptions.strokeColor(Color.RED);
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));
		polygonOptions.add(new LatLng(56.51101750495214, -1.7578125));
		polygonOptions.add(new LatLng(57.657157596582984, 25.0048828125));
		polygonOptions.add(new LatLng(42.09822241118974, 27.8173828125));
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));
		polygonOptions.zIndex(5);

		for (OpenedZone openedZone : openedZones) {
			openedZone.addTo(polygonOptions);
			touchPoints.add(new CircleOptions().center(openedZone.getTouchPoint()).radius(1).fillColor(Color.BLUE));
		}

		map.addPolygon(polygonOptions);
		for (CircleOptions touchPoint : touchPoints) {
			touchPoint.zIndex(15);
			map.addCircle(touchPoint);
		}
	}

	@Override public void showCurrentLocation(LatLng location) {
		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.target(location);
		builder.zoom(18);
		map.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}

	private void addUnexploredZone() {

	}
}
