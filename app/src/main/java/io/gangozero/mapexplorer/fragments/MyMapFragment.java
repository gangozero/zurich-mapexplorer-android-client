package io.gangozero.mapexplorer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import butterknife.OnClick;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.managers.LocManager;
import io.gangozero.mapexplorer.models.Poi;
import io.gangozero.mapexplorer.presenters.ExplorerMapPresenter;
import io.gangozero.mapexplorer.views.ExplorerMapView;

import javax.inject.Inject;
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

	private Marker currentLocationMarker;
	private boolean zoomed;

	@Inject LocManager locManager;

	public static MyMapFragment create() {
		return new MyMapFragment();
	}

	public MyMapFragment() {
		setRetainInstance(true);
	}

	@Override public void onDestroyView() {
		presenter.onViewDestroyed();
		super.onDestroyView();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ExplorerMapPresenter();
		DIHelper.coreComponent().inject(presenter);
		DIHelper.coreComponent().inject(this);
	}

	@Override protected void onMapCreated() {
		addDarkZone();
		initCamera(locManager, null);
		presenter.onViewCreated(this);
		map.setOnMapClickListener(latLng -> locManager.postExternalLocation(latLng));
	}

	@Override public void updateCurrentLocation(LatLng location) {

		if (currentLocationMarker != null) currentLocationMarker.remove();

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_run_black_48dp));
		markerOptions.position(location);
		currentLocationMarker = map.addMarker(markerOptions);

		Log.i("camera", map.getCameraPosition().zoom + "");
		Log.i("camera", map.getCameraPosition().target + "");

		initCamera(locManager, location);//////
	}

	@Override public void showErrorLoading(Throwable t) {
		t.printStackTrace();
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
		//presenter.loadZones(location);
	}
}
