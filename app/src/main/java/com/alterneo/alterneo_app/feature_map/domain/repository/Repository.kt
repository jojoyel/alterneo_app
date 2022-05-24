package com.alterneo.alterneo_app.feature_map.domain.repository

import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal

interface Repository {

    suspend fun getCompanies(page: Int): ArrayDto<CompanyRegistrationDto>

    suspend fun getCompany(id: Int): CompanyRegistrationDto

    suspend fun getCompaniesLocations(page: Int): ArrayDto<CompanyDto>

    suspend fun getCompanyLocation(id: Int): CompanyDto

    suspend fun getCompanyProposals(companyId: Int): ArrayDto<ProposalDto>

    suspend fun getCompanyProposalsCount(companyId: Int): CountDto
}