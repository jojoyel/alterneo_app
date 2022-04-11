package com.alterneo.alterneo_app.responses

open class ArrayResponse<T : BasicResponse>(
    total_returned: Number,
    data: List<T>
)