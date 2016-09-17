package io.gangozero.mapexplorer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.gangozero.mapexplorer.R;

/**
 * Created by eleven on 17/09/2016.
 */
public class FriendsMapFragment extends Fragment {

	public static FriendsMapFragment create() {
		return new FriendsMapFragment();
	}

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_friends_map, container, false);
	}
}
