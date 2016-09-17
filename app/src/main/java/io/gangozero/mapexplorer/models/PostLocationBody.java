package io.gangozero.mapexplorer.models;

/**
 * Created by eleven on 17/09/2016.
 */
public class PostLocationBody {

	public String id;
	public String token;
	public double lat;
	public double lon;
	public String timestamp;

	public PostLocationBody(String id, String token, double lat, double lon, long timestamp) {
		this.id = id;
		this.token = token;
		this.lat = lat;
		this.lon = lon;
		this.timestamp = String.valueOf(timestamp);
	}
}
