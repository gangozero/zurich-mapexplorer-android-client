package io.gangozero.mapexplorer.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import io.gangozero.mapexplorer.App;
import rx.Observable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;

/**
 * Created by eleven on 18/09/2016.
 */
public class LocationManagerImpl implements LocManager, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private final GoogleApiClient googleClient;
	private final LocationManager systemLocationManager;
	private boolean connectionError;
	private App app;

	public LocationManagerImpl(App app) {
		this.app = app;

		GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
		builder.setAccountName("shumvlesu@gmail.com");

		googleClient = new GoogleApiClient.Builder(app)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				//.setAccountName("shumvlesu@gmail.com")
				.addApi(Auth.GOOGLE_SIGN_IN_API, builder.build())
				.build();
		systemLocationManager = (LocationManager) app.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override public Observable<LatLng> getCurrentLocationObservable() {
		return Observable.create(subscriber -> {
			try {

				if (connectionError) {
					Log.e("LocationManagerImpl", "ConnectionError");
					return;
				}

				if (ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}


				googleClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);

				long start = System.currentTimeMillis();
				while (googleClient.isConnecting() && System.currentTimeMillis() - start < 20 * 1000) {
					try {
						Thread.sleep(250, 0);
					} catch (InterruptedException e) {
					}
				}

				if (googleClient.isConnected()) {
					LocationRequest locationRequest = new LocationRequest();
					locationRequest.setInterval(10 * 1000);
					locationRequest.setFastestInterval(5 * 1000);
					locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

					Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleClient);
					if(lastLocation != null){
						subscriber.onNext(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
					}

					Looper.prepare();
					LocationServices.FusedLocationApi.requestLocationUpdates(googleClient, locationRequest, location -> {
						if (!subscriber.isUnsubscribed()) {
							subscriber.onNext(new LatLng(location.getLatitude(), location.getLongitude()));
							//subscriber.onCompleted();
						}
					});
				} else {
					throw new RuntimeException("Not connected client. Timeout");
				}

//				if (connectionResult.isSuccess()) {
//					LocationRequest locationRequest = new LocationRequest();
//					locationRequest.setInterval(10 * 1000);
//					locationRequest.setFastestInterval(5 * 1000);
//					locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//					Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleClient);
//					subscriber.onNext(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
//
//					LocationServices.FusedLocationApi.requestLocationUpdates(googleClient, locationRequest, location -> {
//						if (!subscriber.isUnsubscribed()) {
//							subscriber.onNext(new LatLng(location.getLatitude(), location.getLongitude()));
//							//subscriber.onCompleted();
//						}
//					});
//				} else {
//					throw new RuntimeException("Not connected client");
//				}

				//googleClient.disconnect();

			} catch (Exception e) {
				if (!subscriber.isUnsubscribed()) subscriber.onError(e);
			}
		});
	}

	private boolean isLocationAccessPermitted() {
		if (ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return true;
		}
		return false;
	}

	@Override public void onConnected(@Nullable Bundle bundle) {

	}

	@Override public void onConnectionSuspended(int i) {

	}

	@Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		connectionError = true;
	}

	///

	@Override public boolean isDefaultLoc() {
		return false;
	}

	@Override public LatLng getCurrentLocationAsync() {
		return null;
	}

	@Override public void enableLoc() {

	}

	@Override public void postExternalLocation(LatLng latLng) {

	}
}
