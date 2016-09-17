package io.gangozero.mapexplorer.di.modules;

import dagger.Module;
import dagger.Provides;
import io.gangozero.mapexplorer.App;
import io.gangozero.mapexplorer.managers.*;

import javax.inject.Singleton;

/**
 * Created by eleven on 17/09/2016.
 */
@Module
public class AppModule {

	private App app;

	public AppModule(App app) {
		this.app = app;
	}

	@Provides @Singleton public LocationManager provideLocationManager() {
		return new MockLocationManagerImpl();
	}

	@Provides @Singleton public RestManager provideRestManager() {
//		return new MockRestManager();
		return new RestManagerImpl();
	}

	@Provides @Singleton public KeyValueManager provideKeyValueManager() {
		return new KeyValueManagerImpl(app);
	}

	@Provides @Singleton public NotificationManager provideNotificationManager() {
		return new NotificationManagerImpl(app);
	}
}
