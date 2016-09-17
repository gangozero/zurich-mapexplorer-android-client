package io.gangozero.mapexplorer.activties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import io.gangozero.mapexplorer.R;
import io.gangozero.mapexplorer.fragments.ExplorerMapFragment;

public class MapActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.root_container, ExplorerMapFragment.create())
					.commit();
		}
	}
}
