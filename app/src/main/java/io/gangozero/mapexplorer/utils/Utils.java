package io.gangozero.mapexplorer.utils;

import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.models.OpenedZone;
import io.gangozero.mapexplorer.models.RestLocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class Utils {

	private static final Integer ZERO = new Integer(0);

	public static OpenedZone locToOpenedZone(LatLng location) {
		return new OpenedZone(new LatLng(location.latitude, location.longitude));
	}

	public static List<LatLng> toLatLng(List<RestLocation> list) {
		List<LatLng> result = new ArrayList<>();
		for (RestLocation restLocation : list) {
			result.add(new LatLng(restLocation.lat, restLocation.lon));
		}
		return result;
	}

	public static List<OpenedZone> restLocationsToZones(List<RestLocation> zoneLocations) {
		List<OpenedZone> result = new ArrayList<>();
		for (RestLocation restLocation : zoneLocations) {
			restLocation.lat += 0.000001d;
			restLocation.lon += 0.000001d;
			result.add(new OpenedZone(new LatLng(restLocation.lat, restLocation.lon)));
		}

		return result;
	}

	public static List<LatLng> getConvexHull(List<LatLng> enemies) {

		List<LatLng> hull = new ArrayList<LatLng>();
		if (enemies.size() == 0 || enemies.size() == 1) {
			for (LatLng enemy : enemies) {
				hull.add(enemy);
			}
			return hull;
		}
		LatLng leftmost = null;
		double x = Double.MAX_VALUE;

		for (LatLng loc : enemies) {
			if (loc.longitude < x) {
				x = loc.longitude;
				leftmost = loc;
			}
		}

		LatLng pointOnHull = leftmost;

		Iterator<LatLng> tmp = enemies.iterator();
		LatLng endpoint = tmp.next();
		if (endpoint.equals(leftmost)) {
			endpoint = tmp.next();
		}

		int counter = 0;
		while (!(endpoint.equals(leftmost)) && counter < enemies.size()) {
			hull.add(pointOnHull);
			endpoint = null;
			for (LatLng enemy : enemies) {
				if (endpoint == null) {
					if (!enemy.equals(pointOnHull)) endpoint = enemy;
					else continue;
				}
				if (enemy.equals(endpoint) || enemy.equals(pointOnHull)) continue;
//				double angle1 = enemy.sub(pointOnHull).getAngle();
				double angle1 = getAngle(enemy.latitude, enemy.longitude, pointOnHull.latitude, pointOnHull.longitude);//enemy.sub(pointOnHull).getAngle();
//				double angle2 = endpoint.sub(pointOnHull).getAngle();
				double angle2 = getAngle(endpoint.latitude, endpoint.longitude, pointOnHull.latitude, pointOnHull.longitude);//endpoint.sub(pointOnHull).getAngle();
				double diff = angle1 - angle2;
				if (diff < 0) diff += Math.PI * 2;
				if (diff > 2 * Math.PI) diff -= Math.PI * 2;
				if (diff < Math.PI) {
					endpoint = enemy;
				}
			}
			//lines.add(new Line(pointOnHull, endpoint));
			pointOnHull = endpoint;
			counter++;
		}

		return hull;
	}

	private static double getAngle(double lat1, double long1, double lat2, double long2) {

		double dLon = (long2 - long1);

		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(dLon);

		double brng = Math.atan2(y, x);

		brng = Math.toDegrees(brng);
		brng = (brng + 360) % 360;
		brng = 360 - brng;

		return brng;
	}

//	private static List<LatLng> findConvexHull(final List<LatLng> vertices)
//	{
//		if (vertices == null)
//			return Collections.emptyList();
//
//		if (vertices.size() < 3)
//			return vertices;
//
//		final List<LatLng> points = new ArrayList<LatLng>(vertices);
//		final List<LatLng> hull = new ArrayList<LatLng>();
//		LatLng pointOnHull = getExtremePoint(points, true);
//		LatLng endpoint = null;
//		do
//		{
//			hull.add(pointOnHull);
//			endpoint = points.get(0);
//
//			for (final LatLng r : points)
//			{
//				// Distance is used to find the outermost point -
//				final int turn = findTurn(pointOnHull, endpoint, r);
//				if (endpoint.equals(pointOnHull) || turn == -1 || turn == 0
//						&& dist(pointOnHull, r) > dist(endpoint, pointOnHull))
//				{
//					endpoint = r;
//				}
//			}
//			pointOnHull = endpoint;
//		} while (!endpoint.equals(hull.get(0))); // we are back at the start
//
//		return hull;
//	}
//
//
//	private static double dist(final LatLng p, final LatLng q)
//	{
//		final double dx = (q.x - p.x);
//		final double dy = (q.y - p.y);
//		return dx * dx + dy * dy;
//	}
//
//
//	/**
//	 * Returns -1, 0, 1 if p,q,r forms a right, straight, or left turn.
//	 * 1 = left, -1 = right, 0 = none
//	 *
//	 * @ref http://www-ma2.upc.es/geoc/mat1q1112/OrientationTests.pdf
//	 * @param p
//	 * @param q
//	 * @param r
//	 * @return 1 = left, -1 = right, 0 = none
//	 */
//	private static int findTurn(final Point p, final Point q, final Point r)
//	{
//		final int x1 = (q.x - p.x) * (r.y - p.y);
//		final int x2 = (r.x - p.x) * (q.y - p.y);
//		final int anotherInteger = x1 - x2;
//		return ZERO.compareTo(anotherInteger);
//	}

}
