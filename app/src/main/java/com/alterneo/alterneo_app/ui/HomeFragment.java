package com.alterneo.alterneo_app.ui;

import com.alterneo.alterneo_app.databinding.FragmentHomeBinding;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class HomeFragment extends FragmentStructure<FragmentHomeBinding> {
	@Override
	protected Class<FragmentHomeBinding> getClassBinding() {
		return FragmentHomeBinding.class;
	}

	@Override
	protected void viewCreated() {

		MapView mapView = binding.mapView;
		mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
	}
}
