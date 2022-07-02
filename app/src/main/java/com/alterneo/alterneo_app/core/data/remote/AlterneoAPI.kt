package com.alterneo.alterneo_app.core.data.remote

import com.alterneo.alterneo_app.feature_login.data.remote.dto.LoginDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import com.alterneo.alterneo_app.feature_register.domain.model.SignupModel
import retrofit2.http.*

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

    @POST("/v2/login")
    suspend fun login(
        @Body loginBody: HashMap<String, String>,
        @Header("user-agent") userAgent: String
    ): LoginDto

    @GET("/v2/user/{id}")
    suspend fun getUser(@Path("id") userId: Int): UserDto

    @POST("/v2/signup")
    suspend fun signup(@Body body: SignupModel)
}