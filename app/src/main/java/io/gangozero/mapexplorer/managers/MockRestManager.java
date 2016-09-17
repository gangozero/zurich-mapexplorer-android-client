package io.gangozero.mapexplorer.managers;

import io.gangozero.mapexplorer.models.Poi;
import io.gangozero.mapexplorer.models.PoiResponse;
import io.gangozero.mapexplorer.models.PutPointResponse;
import io.gangozero.mapexplorer.models.RestLocation;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class MockRestManager implements RestManager {
	@Override public RestManagerImpl.Api api() {
		return new RestManagerImpl.Api() {
			@Override public Observable<List<RestLocation>> getAllMap(@Header("token") String token, @Header("consumerKey") String consumerKey) {
				List<RestLocation> result = new ArrayList<>();
				RestLocation loc = new RestLocation();
				loc.lat = 47.36766423537108;
				loc.lon = 8.526935577392578;
				result.add(loc);
				return Observable.just(result);
			}

			@Override public Observable<PutPointResponse> putPoint(@Query("lat") double lat, @Query("lon") double lon) {
				PutPointResponse response = new PutPointResponse();
				response.xp = 10;
				return Observable.just(response);
			}

			@Override public Observable<PoiResponse> getPoi(@Query("lat") double lat, @Query("lon") double lon) {
				PoiResponse poiResponse = new PoiResponse();
				poiResponse.pois = new Poi[]{
						new Poi(),
						new Poi()
				};
				return Observable.just(poiResponse);
			}
		};
	}
}
