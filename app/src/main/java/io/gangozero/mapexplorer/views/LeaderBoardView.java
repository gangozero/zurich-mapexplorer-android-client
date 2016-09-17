package io.gangozero.mapexplorer.views;

import io.gangozero.mapexplorer.models.UserModel;

import java.util.List;

/**
 * Created by eleven on 18/09/2016.
 */
public interface LeaderBoardView {
	void showUsers(List<UserModel> users);

	void showError(Throwable throwable);
}
