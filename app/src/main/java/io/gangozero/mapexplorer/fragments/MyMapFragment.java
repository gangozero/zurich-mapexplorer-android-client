package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.*;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.Poi;
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
public class MyMapFragment extends BaseMapFragment implements ExplorerMapView {

	private ExplorerMapPresenter presenter;
	private Polygon currentPolygon;
	private Marker currentLocationMarker;
	private boolean zoomed;

	@BindView(R.id.text_status) TextView textStatus;
	@BindView(R.id.btn_retry) Button btnRetry;


	public static MyMapFragment create() {
		return new MyMapFragment();
	}

	public MyMapFragment() {
		setRetainInstance(true);
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, result);
		textStatus.setText(R.string.loading);
		btnRetry.setVisibility(View.GONE);
		return result;
	}

	@Override public void onDestroyView() {
		presenter.onViewDestroyed();
		super.onDestroyView();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ExplorerMapPresenter();
		DIHelper.coreComponent().inject(presenter);
	}

	@Override protected void onMapCreated() {
		addDarkZone();
		initCamera();
		presenter.onViewCreated(this);
	}

	@Override public void updateZones(List<OpenedZone> openedZones) {
		List<CircleOptions> touchPoints = new ArrayList<>();
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);
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

		addDarkZonePolygon(polygonOptions);

		for (CircleOptions touchPoint : touchPoints) {
			touchPoint.zIndex(15);
			map.addCircle(touchPoint);
		}

		textStatus.setVisibility(View.GONE);
	}

	@Override public void updateCurrentLocation(LatLng location) {

		if (currentLocationMarker != null) currentLocationMarker.remove();

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_run_black_48dp));
		markerOptions.position(location);
		currentLocationMarker = map.addMarker(markerOptions);

		Log.i("camera", map.getCameraPosition().zoom + "");
		Log.i("camera", map.getCameraPosition().target + "");
	}

	@Override public void showErrorLoading(Throwable t) {
		textStatus.setText(t.getMessage());
		btnRetry.setVisibility(View.VISIBLE);
	}

	@Override public void updatePoi(List<Poi> pois) {

		for (Poi poi : pois) {
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_account_balance_white_48dp));
			markerOptions.position(new LatLng(poi.lat, poi.lon));
			map.addMarker(markerOptions);
		}
	}

	@Override public void showLoading() {
		textStatus.setText(R.string.loading);
		btnRetry.setVisibility(View.GONE);
	}

	@OnClick(R.id.btn_retry) public void handleRetry() {
		presenter.loadZones();
	}

	private void addDarkZone() {
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);

//		polygonOptions.add(new LatLng(-80, -170));//0, 0
//		polygonOptions.add(new LatLng(80, -170));//00, 0
//		polygonOptions.add(new LatLng(80, 170));//00, 180
//		polygonOptions.add(new LatLng(-80, 170));// 0, 180
//		polygonOptions.add(new LatLng(-80, -170));// 0, 0

		polygonOptions.add(new LatLng(0, 0));//0, 0
		polygonOptions.add(new LatLng(89, 0));//90, 0
		polygonOptions.add(new LatLng(89, 89));//90, 180
		polygonOptions.add(new LatLng(0, 89));// 0, 180
		polygonOptions.add(new LatLng(0, 0));// 0, 0
//		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));//0, 0
//		polygonOptions.add(new LatLng(56.51101750495214, -1.7578125));//90, 0
//		polygonOptions.add(new LatLng(57.657157596582984, 25.0048828125));//90, 180
//		polygonOptions.add(new LatLng(42.09822241118974, 27.8173828125));// 0, 180
//		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));// 0, 0

		addDarkZonePolygon(polygonOptions);
	}

	private void addDarkZonePolygon(PolygonOptions polygonOptions) {
		if (currentPolygon != null) currentPolygon.remove();
		currentPolygon = map.addPolygon(polygonOptions);
	}

	private void initCamera() {
		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.target(new LatLng(47.37347170348754, 8.543283641338347));
		builder.zoom(17.031239f);
		map.moveCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}
}
