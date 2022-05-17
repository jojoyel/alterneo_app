package com.alterneo.alterneo_app.feature_map.data.remote.dto

data class ApplicationDto(
    val application_time: Long,
    val cover_text: String,
    val id: Int,
    val proposal_id: Int,
    val user_id: Int
)