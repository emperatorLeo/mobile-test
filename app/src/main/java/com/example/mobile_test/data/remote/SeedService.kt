package com.example.mobile_test.data.remote

import com.example.mobile_test.data.model.SeedResponse
import retrofit2.Response
import retrofit2.http.GET

interface SeedService {

    @GET("/default/random-qr-seed_seed")
    suspend fun getSeed(): Response<SeedResponse>
}