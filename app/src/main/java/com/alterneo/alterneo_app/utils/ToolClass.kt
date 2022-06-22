package com.alterneo.alterneo_app.utils

class ToolClass {
    companion object {
        fun join(vararg data: Any): String {
            val result = ""
            for ((i, d) in data.withIndex()) {
                result.plus(d)
                result.plus(if (i < data.size) "/" else "")
            }

            return result
        }
    }
}