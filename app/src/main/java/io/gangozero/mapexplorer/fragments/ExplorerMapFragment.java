package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.*;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.di.DIHelper;
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
	@BindView(R.id.text_status) TextView textStatus;
	private Polygon currentPolygon;

	public static ExplorerMapFragment create() {
		return new ExplorerMapFragment();
	}

	public ExplorerMapFragment() {
		setRetainInstance(true);
	}

	@Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View result = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, result);
		textStatus.setText(R.string.loading);
		return result;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new ExplorerMapPresenter();
		DIHelper.coreComponent().inject(presenter);
	}

	@Override protected void onMapCreated() {
		addDarkZone();
		presenter.onViewCreated(this);
	}

	@Override public void showZones(List<OpenedZone> openedZones) {
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

	@Override public void showCurrentLocation(LatLng location) {
		CameraPosition.Builder builder = new CameraPosition.Builder();
		builder.target(location);
		builder.zoom(18);
		map.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
	}

	private void addDarkZone() {
		PolygonOptions polygonOptions = new PolygonOptions();

		polygonOptions.fillColor(Color.BLACK);
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));
		polygonOptions.add(new LatLng(56.51101750495214, -1.7578125));
		polygonOptions.add(new LatLng(57.657157596582984, 25.0048828125));
		polygonOptions.add(new LatLng(42.09822241118974, 27.8173828125));
		polygonOptions.add(new LatLng(43.29320031385282, 0.4833984375));

		addDarkZonePolygon(polygonOptions);
	}

	private void addDarkZonePolygon(PolygonOptions polygonOptions) {
		if (currentPolygon != null) currentPolygon.remove();
		currentPolygon = map.addPolygon(polygonOptions);
	}
}
