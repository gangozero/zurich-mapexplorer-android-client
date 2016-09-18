package io.gangozero.mapexplorer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import io.gangozero.mapexplorer.fragments.FriendsMapFragment;
import io.gangozero.mapexplorer.fragments.LeaderBoardFragment;
import io.gangozero.mapexplorer.fragments.MyMapFragment;
import io.gangozero.mapexplorer.fragments.ProfileFragment;

/**
 * Created by eleven on 17/09/2016.
 */
public class MainAdapter extends FragmentPagerAdapter {

	public MainAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override public Fragment getItem(int position) {
		if (position == 0)
			return FriendsMapFragment.create();
		else if (position == 1)
			return MyMapFragment.create();
		else if (position == 2)
			return LeaderBoardFragment.create();
		else
			return ProfileFragment.create();
	}

	@Override public int getCount() {
		return 4;
	}
}
