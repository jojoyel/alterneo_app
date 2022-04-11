package com.alterneo.alterneo_app.utils;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

	private static Client client = null;

	private API api;

	private Client(Context context) {
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://alterneo.alwaysdata.net/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		this.api = retrofit.create(API.class);
	}

	public static Client getClient(Context context) {
		if (client == null) {
			client = new Client(context);
		}

		return client;
	}

//	public void addInterceptor(String key) {
//		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//		httpClient.addInterceptor(chain -> {
//			Request request = chain.request().newBuilder().addHeader("app-auth", key).build();
//			return chain.proceed(request);
//		});
//		if (BuildConfig.BUILD_TYPE.equals("debug")) {
//			httpClient.addInterceptor(interceptor);
//		}
//		Retrofit retrofit = new Retrofit.Builder()
//			.addConverterFactory(GsonConverterFactory.create())
//			.baseUrl("https://api.nextfor.fr/")
//			.client(httpClient.build())
//			.build();
//		this.api = retrofit.create(API.class);
//	}

	public API getApi() {
		return api;
	}
}
