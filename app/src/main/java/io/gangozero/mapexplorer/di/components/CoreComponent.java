package io.gangozero.mapexplorer.di.components;

import dagger.Component;
import io.gangozero.mapexplorer.activties.MainActivity;
import io.gangozero.mapexplorer.broadcasts.CoreBroadcastReceiver;
import io.gangozero.mapexplorer.di.modules.AppModule;
import io.gangozero.mapexplorer.fragments.FriendsMapFragment;
import io.gangozero.mapexplorer.fragments.MyMapFragment;
import io.gangozero.mapexplorer.presenters.ExplorerMapPresenter;
import io.gangozero.mapexplorer.presenters.FriendsMapPresenter;
import io.gangozero.mapexplorer.presenters.LeaderBoardPresenter;

import javax.inject.Singleton;

/**
 * Created by eleven on 17/09/2016.
 */
@Singleton
@Component(modules = AppModule.class)
public interface CoreComponent {
	void inject(ExplorerMapPresenter presenter);

	void inject(MainActivity mainActivity);

	void inject(CoreBroadcastReceiver coreBroadcastReceiver);

	void inject(MyMapFragment myMapFragment);

	void inject(FriendsMapPresenter presenter);

	void inject(FriendsMapFragment friendsMapFragment);

	void inject(LeaderBoardPresenter presenter);
}
