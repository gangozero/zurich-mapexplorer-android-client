package io.gangozero.mapexplorer.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.managers.LocManager;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.RestLocation;
import io.gangozero.mapexplorer.presenters.FriendsMapPresenter;
import io.gangozero.mapexplorer.utils.Utils;
import io.gangozero.mapexplorer.views.FriendsMapView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class FriendsMapFragment extends BaseMapFragment implements FriendsMapView {

	private FriendsMapPresenter presenter;
	@Inject LocManager locManager;

	private List<OpenedZone> zones = new ArrayList<>();

	public static FriendsMapFragment create() {
		return new FriendsMapFragment();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		presenter = new FriendsMapPresenter();
		DIHelper.coreComponent().inject(presenter);
		DIHelper.coreComponent().inject(this);
	}

	@Override protected void onMapCreated() {
		presenter.onViewCreated(this);
		addDarkZone();
		initCamera(locManager, null);
	}

	@Override public void onError(Throwable throwable) {
		btnRetry.setVisibility(View.VISIBLE);
		btnRetry.setText(throwable.getMessage());
	}

	private boolean l;

	@Override public void showMaps(List<RestLocation> locations) {

		textStatus.setVisibility(View.GONE);

//		List<LatLng> locs = Utils.toLatLng(locations);
//		List<LatLng> convexHull = Utils.getConvexHull(locs);
//		List<OpenedZone> zones = new ArrayList<>();
//
//		PolygonOptions polygonOptions = new PolygonOptions();
//		polygonOptions.fillColor(Color.RED);
//
//		for (LatLng latLng : convexHull) {
//			polygonOptions.addHole(convexHull);
//		}

		List<OpenedZone> zones = Utils.restLocationsToZones(locations);
		int color;
		if (l) color = Color.RED;
		else color = Color.BLUE;

		for (OpenedZone zone : zones) {
			PolygonOptions polygonOptions = new PolygonOptions();
			List<LatLng> latLngs = zone.buildBounds();
			for (LatLng latLng : latLngs) {
				polygonOptions.add(latLng);
			}

			polygonOptions.fillColor(color);

			map.addPolygon(polygonOptions);
		}

		l = !l;

//		PolygonOptions polygonOptions = new PolygonOptions();
//		for (OpenedZone zone : zones) {
//			zone.addHoleTo(polygonOptions);
//		}
		//map.addPolygon(polygonOptions);
		//updateZones(zones);
	}
}
