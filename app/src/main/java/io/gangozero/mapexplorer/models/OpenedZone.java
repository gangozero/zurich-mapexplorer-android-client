package io.gangozero.mapexplorer.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class OpenedZone {

	private LatLng leftBottom;
	private LatLng leftTop;
	private LatLng rightTop;
	private LatLng rightBottom;
	private LatLng touchPoint;

	public OpenedZone(LatLng touchPoint) {
		this.touchPoint = touchPoint;
		setBoundaries();
	}

	public void addTo(PolygonOptions polygonOptions) {
		List<LatLng> bounds = new ArrayList<>();

		double offset = 0.0000001d;
		LatLng leftBottomTmp = new LatLng(leftBottom.latitude + offset, leftBottom.longitude + offset);
		LatLng leftTopTmp = new LatLng(leftTop.latitude - offset, leftTop.longitude + offset);
		LatLng rightTopTmp = new LatLng(rightTop.latitude - offset, rightTop.longitude - offset);
		LatLng rightBottomTmp = new LatLng(rightBottom.latitude + offset, rightBottom.longitude - offset);

		bounds.add(leftBottomTmp);
		bounds.add(leftTopTmp);
		bounds.add(rightTopTmp);
		bounds.add(rightBottomTmp);
		bounds.add(leftBottomTmp);

		polygonOptions.addHole(bounds);
	}

	public LatLng getTouchPoint() {
		return touchPoint;
	}

	private void setBoundaries() {

		final double latRation = 0.001d;
		final double lonRation = 0.0015d;
		double lat = (Math.floor((touchPoint.latitude + 180d) / latRation)) * latRation - 180d;
		double lon = (Math.floor((touchPoint.longitude + 90d) / lonRation)) * lonRation - 90d;

		this.leftBottom = new LatLng(lat, lon);
		this.leftTop = new LatLng(lat + latRation, lon);
		this.rightTop = new LatLng(lat + latRation, lon + lonRation);
		this.rightBottom = new LatLng(lat, lon + lonRation);
	}

	public boolean isEqual(OpenedZone zone){
		return zone.leftBottom.equals(leftBottom);
	}

}
