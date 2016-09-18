package io.gangozero.mapexplorer.activties;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.adapters.MainAdapter;
import io.gangozero.mapexplorer.di.DIHelper;
import io.gangozero.mapexplorer.managers.NotificationManager;

import javax.inject.Inject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

	public @Inject NotificationManager notificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		DIHelper.coreComponent().inject(this);
		//notificationManager.showMockMovementStart();

		ActivityCompat.requestPermissions(this, new String[]{
				ACCESS_FINE_LOCATION,
				ACCESS_COARSE_LOCATION
		}, 1000);

		if (savedInstanceState == null) {

			ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
			MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
			viewPager.setAdapter(adapter);

			findViewById(R.id.btn_friends_map).setOnClickListener(v -> viewPager.setCurrentItem(0));
			findViewById(R.id.btn_my_map).setOnClickListener(v -> viewPager.setCurrentItem(1));
			findViewById(R.id.btn_leader_board).setOnClickListener(v -> viewPager.setCurrentItem(2));
			//findViewById(R.id.btn_profile).setOnClickListener(v -> viewPager.setCurrentItem(3));

			viewPager.setCurrentItem(1);
		}
	}
}
