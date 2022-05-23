package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApplicationDto(
    @SerializedName("application_time") val applicationTime: Long,
    @SerializedName("cover_text") val coverText: String,
    val id: Int,
    @SerializedName("proposal_id") val proposalId: Int,
    @SerializedName("user_id") val userId: Int
)