package com.project.somnium.data.repository

import com.project.somnium.BuildConfig
import com.project.somnium.data.RecyclerDataModel
import com.project.somnium.data.remote.ApiService
import com.project.somnium.data.remote.ImageRequestBody
import javax.inject.Inject

class GptRepository @Inject constructor(
    private val service: ApiService
) {
    private val apikey = "Bearer ${BuildConfig.Apikey}"

    suspend fun requestImage(prompt: String): Result<RecyclerDataModel> {
        return try {
            val request = ImageRequestBody(prompt)
            val response = service.postGptApiImage(apikey, request)

            if (response.isSuccessful) {
                val result = response.body()
                val url = result?.data?.getOrNull(0)?.url

                if (url != null) {
                    Result.success(RecyclerDataModel(prompt, url))
                } else {
                    Result.failure(Exception("이미지 URL이 없습니다."))
                }
            } else {
                Result.failure(Exception("API 호출 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}