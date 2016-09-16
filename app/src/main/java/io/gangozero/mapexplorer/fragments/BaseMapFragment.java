package io.gangozero.mapexplorer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.MapsInitializer;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import io.gangozero.mapexplorer.R;

/**
 * Created by eleven on 16/09/2016.
 */
public abstract class BaseMapFragment extends Fragment {

	protected MapView mapView;
	protected MapboxMap map;

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
		MapboxAccountManager.start(getContext(), getString(R.string.MAP_BOX_KEY));
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fragment_map, container, false);
		MapsInitializer.initialize(getContext());
		mapView = (MapView) result.findViewById(R.id.map_view);
		//mapView.setStyle("mapbox://styles/repa40x/cit68jtd100ah2xovmxedtq3y");
//		mapView.setStyle(Style.SATELLITE);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(this::onMapCreated);
		return result;
	}

	private void onMapCreated(MapboxMap googleMap) {
		this.map = googleMap;

		onMapCreated();
	}

	protected abstract void onMapCreated();
}
