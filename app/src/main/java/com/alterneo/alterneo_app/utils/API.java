package com.alterneo.alterneo_app.utils;

import com.alterneo.alterneo_app.responses.CompanyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

	@GET("company")
	Call<CompanyResponse> getCompany(@Query("id") int id);
}
