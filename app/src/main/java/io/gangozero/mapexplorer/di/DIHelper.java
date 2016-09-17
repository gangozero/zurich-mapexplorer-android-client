package io.gangozero.mapexplorer.di;

import io.gangozero.mapexplorer.App;
import io.gangozero.mapexplorer.di.components.CoreComponent;
import io.gangozero.mapexplorer.di.components.DaggerCoreComponent;
import io.gangozero.mapexplorer.di.modules.AppModule;

/**
 * Created by eleven on 17/09/2016.
 */
public class DIHelper {

	private static CoreComponent coreComponent;

	public static void init(App app) {
		coreComponent = DaggerCoreComponent
				.builder()
				.appModule(new AppModule(app))
				.build();
	}

	public static CoreComponent coreComponent() {
		return coreComponent;
	}
}
