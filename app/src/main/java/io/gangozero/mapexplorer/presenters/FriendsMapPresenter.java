package io.gangozero.mapexplorer.presenters;

import android.util.Log;
import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.LocationManager;
import io.gangozero.mapexplorer.managers.NotificationManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.RestLocation;
import io.gangozero.mapexplorer.models.UserModel;
import io.gangozero.mapexplorer.views.FriendsMapView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by eleven on 17/09/2016.
 */
public class FriendsMapPresenter {

	@Inject public LocationManager locationManager;
	@Inject public RestManager restManager;
	@Inject public KeyValueManager keyValueManager;
	@Inject public NotificationManager notificationManager;

	private FriendsMapView view;

	public void onViewCreated(FriendsMapView view) {
		this.view = view;

		loadMaps();
	}

	private void loadMaps() {
		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

		restManager
				.api()
				.getFriends(
						userToken,
						userId
				).flatMap(new Func1<List<UserModel>, Observable<List<RestLocation>>>() {
			@Override public Observable<List<RestLocation>> call(List<UserModel> userModels) {

				return Observable.from(userModels).flatMap(new Func1<UserModel, Observable<List<RestLocation>>>() {
					@Override public Observable<List<RestLocation>> call(UserModel userModel) {
						return restManager.api().getMap(
								userModel.id,
								userToken,
								userModel.id
						);
					}
				});
			}
		})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(locations -> view.showMaps(locations));
	}

	public void onViewDestroyed() {

	}
}