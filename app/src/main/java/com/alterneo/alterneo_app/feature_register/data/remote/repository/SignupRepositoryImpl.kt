package com.alterneo.alterneo_app.feature_register.data.remote.repository

import com.alterneo.alterneo_app.core.data.remote.AlterneoAPI
import com.alterneo.alterneo_app.feature_register.domain.model.SignupModel
import com.alterneo.alterneo_app.feature_register.domain.repository.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val api: AlterneoAPI) : SignupRepository {
    override suspend fun userSignup(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        accountType: Int
    ) =
        api.signup(
            SignupModel(
                email = email,
                password = password,
                firstName = firstName,
                lastName = lastName,
                accountType = accountType
            )
        )

    override suspend fun companySignup(email: String, password: String, accountType: Int) =
        api.signup(
            SignupModel(
                email = email,
                password = password,
                accountType = accountType
            )
        )
}