package io.gangozero.mapexplorer.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import io.gangozero.mapexplorer.App;

/**
 * Created by eleven on 17/09/2016.
 */
public class KeyValueManagerImpl implements KeyValueManager {

	private final SharedPreferences sharedPreferences;

	public KeyValueManagerImpl(App app) {
		sharedPreferences = app.getSharedPreferences("default-storage", Context.MODE_PRIVATE);
		sharedPreferences.edit().putString(KeyValueManager.USER_ID, "user5").apply();
		sharedPreferences.edit().putString(KeyValueManager.USER_TOKEN, "123").apply();
	}

	@Override public void putString(String key, String value) {
		sharedPreferences.edit().putString(key, value).apply();
	}

	@Nullable @Override public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}
}
