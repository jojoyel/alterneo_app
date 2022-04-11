package com.alterneo.alterneo_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alterneo.alterneo_app.ui.HomeFragment;
import com.alterneo.alterneo_app.ui.HomeFragmentBis;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.setFragment(new HomeFragmentBis());

	}

	public void setFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.flMain, fragment).commitAllowingStateLoss();
	}

	public void setFragment(Fragment fragment, Bundle args) {
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.flMain, fragment.getClass(), args).commitAllowingStateLoss();
	}
}