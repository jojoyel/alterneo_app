package com.alterneo.alterneo_app.feature_map.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArrayDto<T>(
    @SerializedName("total_returned") val totalReturned: Int,
    val data: List<T>
)