package com.alterneo.alterneo_app.feature_map.domain.use_case

import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyRegistrationDto
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCompanyRegistrationUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: Int = 0): Flow<Resource<CompanyRegistrationDto>> = flow {
        try {
            emit(Resource.Loading<CompanyRegistrationDto>())
            val company = repository.getCompany(id)
            emit(Resource.Success<CompanyRegistrationDto>(company))
        } catch (e: HttpException) {
            emit(Resource.Error<CompanyRegistrationDto>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<CompanyRegistrationDto>("Couldn't reach server. Check your internet connection"))
        }
    }
}