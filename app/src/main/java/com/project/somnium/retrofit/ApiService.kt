package com.project.somnium.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("v1/images/generations")
    fun postGptApiImage(
        @Header("Authorization") authHeader: String,
        @Body requestBody: ImageRequestBody
    ): Call<ApiDataModel?>?

}