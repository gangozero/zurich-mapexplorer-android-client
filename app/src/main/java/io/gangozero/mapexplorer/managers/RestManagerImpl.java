package io.gangozero.mapexplorer.managers;

import io.gangozero.mapexplorer.models.PoiResponse;
import io.gangozero.mapexplorer.models.PutPointResponse;
import io.gangozero.mapexplorer.models.RestLocation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class RestManagerImpl implements RestManager {

	private final Api api;

	public RestManagerImpl() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//		interceptor.setLevel(RestAdapter.LogLevel.FULL);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		api = new Retrofit.Builder()
				.baseUrl("")
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build().create(Api.class);
	}

	@Override public Api api() {
		return api;
	}

	public interface Api {

		@GET
		Observable<List<RestLocation>> getAllMap(@Header("token") String token, @Header("consumerKey") String consumerKey);

		@POST
		Observable<PutPointResponse> putPoint(@Query("lat") double lat, @Query("lon") double lon);

		@GET
		Observable<PoiResponse> getPoi(@Query("lat") double lat, @Query("lon") double lon);
	}
}