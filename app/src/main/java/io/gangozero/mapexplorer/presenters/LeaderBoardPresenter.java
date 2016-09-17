package io.gangozero.mapexplorer.presenters;

import io.gangozero.mapexplorer.managers.KeyValueManager;
import io.gangozero.mapexplorer.managers.RestManager;
import io.gangozero.mapexplorer.models.UserModel;
import io.gangozero.mapexplorer.views.LeaderBoardView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by eleven on 18/09/2016.
 */
public class LeaderBoardPresenter {

	@Inject RestManager restManager;
	@Inject KeyValueManager keyValueManager;
	private LeaderBoardView view;

	public void onViewCreated(LeaderBoardView view) {
		this.view = view;
		loadData();
	}

	private void loadData() {

		String userId = keyValueManager.getString(KeyValueManager.USER_ID);
		String userToken = keyValueManager.getString(KeyValueManager.USER_TOKEN);

		restManager
				.api()
				.getLeaderBoard(
						userId,
						userToken,
						userId
				)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<List<UserModel>>() {
					@Override public void call(List<UserModel> userModels) {
						view.showUsers(userModels);
					}
				}, new Action1<Throwable>() {
					@Override public void call(Throwable throwable) {
						view.showError(throwable);
					}
				});
	}
}
