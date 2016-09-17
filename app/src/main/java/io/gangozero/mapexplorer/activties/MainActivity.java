package io.gangozero.mapexplorer.activties;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.adapters.MainAdapter;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		if (savedInstanceState == null) {

			ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
			MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
			viewPager.setAdapter(adapter);

			findViewById(R.id.btn_friends_map).setOnClickListener(v -> viewPager.setCurrentItem(0));
			findViewById(R.id.btn_my_map).setOnClickListener(v -> viewPager.setCurrentItem(1));
			findViewById(R.id.btn_leader_board).setOnClickListener(v -> viewPager.setCurrentItem(2));
		}
	}
}
