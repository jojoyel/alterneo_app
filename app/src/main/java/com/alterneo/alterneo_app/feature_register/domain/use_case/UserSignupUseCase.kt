package com.alterneo.alterneo_app.feature_register.domain.use_case

import android.content.Context
import com.alterneo.alterneo_app.R
import com.alterneo.alterneo_app.feature_register.domain.repository.SignupRepository
import com.alterneo.alterneo_app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class UserSignupUseCase @Inject constructor(
    private val signupRepository: SignupRepository,
    private val context: Context
) {
    operator fun invoke(
        email: String,
        password: String,
        lastName: String,
        firstName: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val result =
                signupRepository.userSignup(email, password, lastName, firstName, accountType = 1)
            emit(Resource.Success<Unit>(result))
        } catch (e: HttpException) {
            emit(Resource.Error<Unit>(context.getString(R.string.error_occurred)))
        } catch (e: IOException) {
            emit(Resource.Error<Unit>(context.getString(R.string.error_joining_server)))
        }
    }
}