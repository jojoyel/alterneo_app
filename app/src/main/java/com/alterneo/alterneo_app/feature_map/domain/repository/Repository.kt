package com.alterneo.alterneo_app.feature_map.domain.repository

import com.alterneo.alterneo_app.feature_map.data.remote.dto.ArrayDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyRegistrationDto

interface Repository {

    suspend fun getCompanies(): ArrayDto<CompanyDto>

    suspend fun getCompaniesRegistration(): ArrayDto<CompanyRegistrationDto>
}