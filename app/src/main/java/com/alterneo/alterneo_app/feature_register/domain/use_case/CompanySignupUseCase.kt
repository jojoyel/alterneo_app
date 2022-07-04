package com.alterneo.alterneo_app.feature_register.domain.use_case

import android.content.Context
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_register.domain.repository.SignupRepository
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CompanySignupUseCase @Inject constructor(
    private val signupRepository: SignupRepository,
    private val context: Context
) {
    operator fun invoke(
        email: String,
        password: String,
        name: String,
        url: String,
        tel: String,
        postalCode: String,
        siret: String,
        address: String,
        city: String,
        businessSectorId: Int,
        juridicalStatusId: Int
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val result = signupRepository.companySignup(
                email = email,
                password = password,
                name = name,
                url = url,
                tel = tel,
                postalCode = postalCode,
                siret = siret,
                addres = address,
                city = city,
                businessSectorId = businessSectorId,
                juridicalStatusId = juridicalStatusId
            )
            emit(Resource.Success<Unit>(result))
        } catch (e: HttpException) {
            emit(Resource.Error<Unit>(context.getString(R.string.error_occurred)))
        } catch (e: IOException) {
            emit(Resource.Error<Unit>(context.getString(R.string.error_joining_server)))
        }
    }
}
