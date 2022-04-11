package com.alterneo.alterneo_app.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alterneo.alterneo_app.R;
import com.alterneo.alterneo_app.databinding.FragmentHomeBinding;
import com.alterneo.alterneo_app.models.Company;
import com.alterneo.alterneo_app.responses.CompaniesResponse;
import com.alterneo.alterneo_app.responses.CompanyResponse;
import com.alterneo.alterneo_app.utils.Client;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;

import java.lang.annotation.Annotation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentBis extends FragmentStructure<FragmentHomeBinding> {

	public static final String TAG = "HomeFragmentBis";

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

}
