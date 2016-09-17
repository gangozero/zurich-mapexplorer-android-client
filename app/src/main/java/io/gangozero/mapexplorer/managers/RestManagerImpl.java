package io.gangozero.mapexplorer.managers;

import io.gangozero.mapexplorer.models.*;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
				.baseUrl("https://dbvmcgu4i2.execute-api.eu-west-1.amazonaws.com/")
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build().create(Api.class);
	}

	@Override public Api api() {
		return api;
	}

	public interface Api {

		@GET("prod/map")
		Observable<List<RestLocation>> getAllMap(
				@Query("user_id") String userId,
				@Query("token") String token,
				@Query("id") String id
		);

		@POST("prod/map")
		Observable<PutPointResponse> postLocation(@Body PostLocationBody body);

		@GET("prod/poi")
		Observable<List<Poi>> getPoi(
				@Query("token") String token,
				@Query("id") String id,
				@Query("lat") double lat,
				@Query("lon") double lon
		);
	}
}