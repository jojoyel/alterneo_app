package com.alterneo.alterneo_app.feature_map.domain.use_case

import com.alterneo.alterneo_app.feature_map.data.remote.dto.ArrayDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.ProposalDto
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCompanyProposalUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Int): Flow<Resource<ArrayDto<ProposalDto>>> = flow {
        try {
            emit(Resource.Loading<ArrayDto<ProposalDto>>())
            val proposals = repository.getCompanyProposals(id)
            emit(Resource.Success<ArrayDto<ProposalDto>>(proposals))
        } catch (e: HttpException) {
            emit(Resource.Error<ArrayDto<ProposalDto>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<ArrayDto<ProposalDto>>("Couldn't reach server. Check your internet connection"))
        }
    }
}
