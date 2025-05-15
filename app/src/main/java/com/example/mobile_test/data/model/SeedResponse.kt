package com.example.mobile_test.data.model

import com.google.gson.annotations.SerializedName

data class SeedResponse(
    @SerializedName("seed") val seed: String,
    @SerializedName("expiresAt") val expiration: String
)
