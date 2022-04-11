package com.alterneo.alterneo_app.ui;

import android.util.Log;

import com.alterneo.alterneo_app.R;
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding;
import com.alterneo.alterneo_app.models.Company;
import com.alterneo.alterneo_app.responses.CompaniesResponse;
import com.alterneo.alterneo_app.responses.CompanyResponse;
import com.alterneo.alterneo_app.utils.Client;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.ViewAnnotationOptions;
import com.mapbox.maps.viewannotation.ViewAnnotation;
import com.mapbox.maps.viewannotation.ViewAnnotationManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends FragmentStructure<FragmentHomeBinding> {

	public static final String TAG = "HomeFragment";

	@Override
	protected Class<FragmentHomeBinding> getClassBinding() {
		return FragmentHomeBinding.class;
	}

	MapView mapView;

	@Override
	protected void viewCreated() {

		mapView = binding.mapView;
		mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

		Call<CompanyResponse> call = Client.getClient(mainActivity).getApi().getCompany(1);
		call.enqueue(new Callback<CompanyResponse>() {
			@Override
			public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
				Log.d(TAG, "onResponse: " + response.body().getName());
				addPinToMap(response.body().toCompany());
			}

			@Override
			public void onFailure(Call<CompanyResponse> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
			}
		});

		Call<CompaniesResponse> call1 = Client.getClient(mainActivity).getApi().getCompanies(0);
		call1.enqueue(new Callback<CompaniesResponse>() {
			@Override
			public void onResponse(Call<CompaniesResponse> call, Response<CompaniesResponse> response) {
				Log.d(TAG, "onResponse: " + response.body().toString());
			}

			@Override
			public void onFailure(Call<CompaniesResponse> call, Throwable t) {
				Log.d(TAG, "onFailure: " + t.getMessage());
			}
		});
	}

	private void addPinToMap(Company company) {
	}
}
