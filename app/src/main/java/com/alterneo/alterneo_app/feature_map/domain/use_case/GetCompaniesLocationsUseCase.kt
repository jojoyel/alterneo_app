package com.alterneo.alterneo_app.feature_map.domain.use_case

import android.content.Context
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_map.data.remote.dto.ArrayDto
import com.alterneo.alterneo_app.feature_map.data.remote.dto.CompanyDto
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCompaniesLocationsUseCase @Inject constructor(
    private val repository: Repository,
    private val context: Context
) {
    operator fun invoke(page: Int = 0): Flow<Resource<ArrayDto<CompanyDto>>> = flow {
        try {
            emit(Resource.Loading<ArrayDto<CompanyDto>>())
            val companies = repository.getCompaniesLocations(page)
            emit(Resource.Success<ArrayDto<CompanyDto>>(companies))
        } catch (e: HttpException) {
            emit(Resource.Error<ArrayDto<CompanyDto>>(e.code().toString()))
        } catch (e: IOException) {
            emit(Resource.Error<ArrayDto<CompanyDto>>(context.getString(R.string.error)))
        }
    }
}