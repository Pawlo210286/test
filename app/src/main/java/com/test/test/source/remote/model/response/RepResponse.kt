package com.test.test.source.remote.model.response

import com.google.gson.annotations.SerializedName

data class RepResponse(
    val id: Long,
    val name: String,
    @SerializedName("created_at")
    val createdAt: String
)