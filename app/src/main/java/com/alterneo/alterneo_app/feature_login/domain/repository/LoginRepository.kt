package com.alterneo.alterneo_app.feature_login.domain.repository

import com.alterneo.alterneo_app.feature_login.data.remote.dto.LoginDto

interface LoginRepository {

    suspend fun login(email: String, password: String): LoginDto
}