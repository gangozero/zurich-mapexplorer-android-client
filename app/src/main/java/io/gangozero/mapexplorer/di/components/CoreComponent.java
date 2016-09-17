package io.gangozero.mapexplorer.di.components;

import dagger.Component;
import io.gangozero.mapexplorer.activties.MainActivity;
import io.gangozero.mapexplorer.di.modules.AppModule;
import io.gangozero.mapexplorer.presenters.ExplorerMapPresenter;

import javax.inject.Singleton;

/**
 * Created by eleven on 17/09/2016.
 */
@Singleton
@Component(modules = AppModule.class)
public interface CoreComponent {
	void inject(ExplorerMapPresenter presenter);

	void inject(MainActivity mainActivity);
}
