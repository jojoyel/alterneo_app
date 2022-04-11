package com.alterneo.alterneo_app.ui;

import android.util.Log;

import com.alterneo.alterneo_app.databinding.FragmentHomeBinding;
import com.alterneo.alterneo_app.responses.CompanyResponse;
import com.alterneo.alterneo_app.utils.Client;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends FragmentStructure<FragmentHomeBinding> {

	public static final String TAG = "HomeFragment";

	@Override
	protected Class<FragmentHomeBinding> getClassBinding() {
		return FragmentHomeBinding.class;
	}

	@Override
	protected void viewCreated() {

		MapView mapView = binding.mapView;
		mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

		Client.getClient(mainActivity).getApi().getCompany(1).enqueue(new Callback<CompanyResponse>() {
			@Override
			public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
				Log.d(TAG, "onResponse: " + response.body());
			}

			@Override
			public void onFailure(Call<CompanyResponse> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
			}
		});
	}
}
