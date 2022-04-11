package com.alterneo.alterneo_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alterneo.alterneo_app.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.setFragment(new HomeFragment());

	}

	public void setFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.flMain, fragment).commitAllowingStateLoss();
	}

	public void setFragment(Fragment fragment, @Nullable Bundle args) {
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.flMain, fragment.getClass(), args).commitAllowingStateLoss();
	}
}