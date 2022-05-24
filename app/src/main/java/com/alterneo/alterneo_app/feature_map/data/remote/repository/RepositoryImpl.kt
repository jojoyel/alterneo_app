package com.alterneo.alterneo_app.feature_map.data.remote.repository

import com.alterneo.alterneo_app.core.data.remote.AlterneoAPI
import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: AlterneoAPI
): Repository {
    override suspend fun getCompanies(page: Int): ArrayDto<CompanyRegistrationDto> {
        return api.getCompanies(page)
    }

    override suspend fun getCompany(id: Int): CompanyRegistrationDto {
        return api.getCompany(id)
    }

    override suspend fun getCompaniesLocations(page: Int): ArrayDto<CompanyDto> {
        return api.getCompaniesLocations(page)
    }

    override suspend fun getCompanyLocation(id: Int): CompanyDto {
        return api.getCompanyLocation(id)
    }

    override suspend fun getCompanyProposals(companyId: Int): ArrayDto<ProposalDto> {
        return api.getCompanyProposals(companyId)
    }

    override suspend fun getCompanyProposalsCount(companyId: Int): CountDto {
        return api.getCompanyProposalsCount(companyId)
    }
}