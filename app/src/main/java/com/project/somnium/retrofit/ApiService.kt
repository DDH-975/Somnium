package com.project.somnium.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("v1/images/generations")
    suspend fun postGptApiImage(
        @Header("Authorization") authHeader: String,
        @Body requestBody: ImageRequestBody
    ): Response<ApiDataModel>
}