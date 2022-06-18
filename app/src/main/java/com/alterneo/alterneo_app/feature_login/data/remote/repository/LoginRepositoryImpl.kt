package com.alterneo.alterneo_app.feature_login.data.remote.repository

import com.alterneo.alterneo_app.core.data.remote.AlterneoAPI
import com.alterneo.alterneo_app.feature_login.domain.repository.LoginRepository
import com.alterneo.alterneo_app.utils.Constants
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: AlterneoAPI) : LoginRepository {
    override suspend fun login(email: String, password: String) =
        api.login(hashMapOf("email" to email, "password" to password), Constants.USER_AGENT_VALUE)
}