package com.alterneo.alterneo_app.feature_login.domain.use_case

import android.content.Context
import android.content.SharedPreferences
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_login.data.remote.dto.LoginDto
import com.alterneo.alterneo_app.feature_login.domain.repository.LoginRepository
import com.alterneo.alterneo_app.utils.Constants
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DoLoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginDto>> = flow {
        try {
            emit(Resource.Loading<LoginDto>())
            val login = loginRepository.login(email, password)
            sharedPreferences.edit()
                .putString(Constants.SHARED_PREF_JWT, login.jwtToken)
                .putString(Constants.SHARED_PREF_REFRESH_JWT, login.refreshToken)
                .putString(Constants.SHARED_PREF_EMAIL, email)
                .apply()
            emit(Resource.Success<LoginDto>(login))
        } catch (e: HttpException) {
            emit(
                Resource.Error<LoginDto>(
                    e.code().toString()
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<LoginDto>(context.getString(R.string.error_joining_server)))
        }
    }
}