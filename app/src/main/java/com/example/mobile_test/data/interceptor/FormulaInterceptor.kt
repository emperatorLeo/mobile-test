package com.example.mobile_test.data.interceptor

import com.example.mobile_test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class FormulaInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val urlBuilder = request.newBuilder().header(
            "x-api-key",
            BuildConfig.API_KEY
        ).build()

        return chain.proceed(urlBuilder)
    }
}