package com.alterneo.alterneo_app.feature_map.data.remote

import com.alterneo.alterneo_app.feature_map.data.remote.dto.ArrayDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MapAPI {

    @GET("/v1/company/")
    suspend fun getCompanies(@Query("page") page: Int): ArrayDto<CompanyDto>
}