package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.models.OpenedZone;

import java.util.List;

//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * Created by eleven on 16/09/2016.
 */
public abstract class BaseMapFragment extends Fragment {

	protected MapView mapView;
	protected GoogleMap map;

	protected @BindView(R.id.text_status) TextView textStatus;
	protected @BindView(R.id.btn_retry) Button btnRetry;

	private Polygon currentPolygon;

	@Override public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override public void onDestroyView() {
		mapView.onDestroy();
		super.onDestroyView();
	}

	@Override public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//MapboxAccountManager.start(getContext(), getString(R.string.MAP_BOX_KEY));
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_my_map, container, false);
		MapsInitializer.initialize(getContext());
		mapView = (MapView) result.findViewById(R.id.map_view);

		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this::onMapCreated);

		ButterKnife.bind(this, result);
		textStatus.setText(R.string.loading);
		btnRetry.setVisibility(View.GONE);

		return result;
	}

	private void onMapCreated(GoogleMap googleMap) {
		this.map = googleMap;
		onMapCreated();
	}

	protected void addDarkZone() {
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);


		polygonOptions.add(new LatLng(0, 0));//0, 0
		polygonOptions.add(new LatLng(89, 0));//90, 0
		polygonOptions.add(new LatLng(89, 89));//90, 180
		polygonOptions.add(new LatLng(0, 89));// 0, 180
		polygonOptions.add(new LatLng(0, 0));// 0, 0

		addDarkZonePolygon(polygonOptions);
	}

	protected void addDarkZonePolygon(PolygonOptions polygonOptions) {
		if (currentPolygon != null) currentPolygon.remove();
		currentPolygon = map.addPolygon(polygonOptions);
	}

	protected void initCamera(LocationManager locationManager) {

		LatLng loc;
		if (locationManager.isDefaultLoc()) {
			loc = new LatLng(47.37347170348754, 8.543283641338347);
		} else {
			loc = locationManager.getCurrentLocationAsync();
		}

		if (loc == null) return;

		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.target(loc);
		builder.zoom(17.031239f);
		map.moveCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}

	public void updateZones(List<OpenedZone> openedZones) {
		//List<CircleOptions> touchPoints = new ArrayList<>();
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));
		polygonOptions.add(new LatLng(56.51101750495214, -1.7578125));
		polygonOptions.add(new LatLng(57.657157596582984, 25.0048828125));
		polygonOptions.add(new LatLng(42.09822241118974, 27.8173828125));
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));
		polygonOptions.zIndex(5);

		for (OpenedZone openedZone : openedZones) {
			openedZone.addHoleTo(polygonOptions);
			//touchPoints.add(new CircleOptions().center(openedZone.getTouchPoint()).radius(1).fillColor(Color.BLUE));
		}

		addDarkZonePolygon(polygonOptions);

		//for (CircleOptions touchPoint : touchPoints) {
		//touchPoint.zIndex(15);
		//map.addCircle(touchPoint);
		//}

		textStatus.setVisibility(View.GONE);
	}


	protected abstract void onMapCreated();
}
