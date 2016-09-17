package io.gangozero.mapexplorer.managers;

import android.support.annotation.Nullable;

/**
 * Created by eleven on 17/09/2016.
 */
public interface KeyValueManager {

	String LAST_LOC = "latLoc";
	String USER_ID = "userId";
	String USER_TOKEN = "userToken";

	void putString(String key, String value);

	@Nullable String getString(String key);
}
