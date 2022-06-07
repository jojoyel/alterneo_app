package com.alterneo.alterneo_app.core.data.remote

import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlterneoAPI {

    @GET("/v2/company/")
    suspend fun getCompanies(@Query("page") page: Int): ArrayDto<CompanyRegistrationDto>

    @GET("/v2/company/{id}")
    suspend fun getCompany(@Path("id") id: Int): CompanyRegistrationDto

    @GET("/v2/companyLocation/")
    suspend fun getCompaniesLocations(@Query("page") page: Int): ArrayDto<CompanyDto>

    @GET("/v2/companyLocation/{id}")
    suspend fun getCompanyLocation(@Path("id") id: Int): CompanyDto

    @GET("/v2/companyProposals/{id}")
    suspend fun getCompanyProposals(@Path("id") companyId: Int): ArrayDto<ProposalDto>

    @GET("/v2/companyProposalsCount/{id}")
    suspend fun getCompanyProposalsCount(@Path("id") companyId: Int): CountDto
}