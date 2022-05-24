package com.alterneo.alterneo_app.core.data.remote

import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlterneoAPI {

    @GET("/v1/company/")
    suspend fun getCompanies(@Query("page") page: Int): ArrayDto<CompanyRegistrationDto>

    @GET("/v1/company/{id}")
    suspend fun getCompany(@Path("id") id: Int): CompanyRegistrationDto

    @GET("/v1/companyLocation/")
    suspend fun getCompaniesLocations(@Query("page") page: Int): ArrayDto<CompanyDto>

    @GET("/v1/companyLocation/{id}")
    suspend fun getCompanyLocation(@Path("id") id: Int): CompanyDto

    @GET("/v1/companyProposals/{id}")
    suspend fun getCompanyProposals(@Path("id") companyId: Int): ArrayDto<ProposalDto>

    @GET("/v1/companyProposalsCount/{id}")
    suspend fun getCompanyProposalsCount(@Path("id") companyId: Int): CountDto
}