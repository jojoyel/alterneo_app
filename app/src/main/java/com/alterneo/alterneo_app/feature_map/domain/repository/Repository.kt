package com.alterneo.alterneo_app.feature_map.domain.repository

import com.alterneo.alterneo_app.feature_map.data.remote.dto.*

interface Repository {

    suspend fun getCompaniesLocations(page: Int): ArrayDto<CompanyDto>

    suspend fun getCompanyLocation(id: Int): CompanyDto

    suspend fun getCompanies(page: Int): ArrayDto<CompanyRegistrationDto>

    suspend fun getCompany(id: Int): CompanyRegistrationDto

    suspend fun getProposals(companyId: Int): ArrayDto<ProposalDto>

    suspend fun getProposalsCount(companyId: Int): CountDto
}