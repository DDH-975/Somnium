package com.project.somnium.retrofit

import com.project.somnium.BuildConfig
import com.project.somnium.makeImg_Recycler.RecyclerDataModel

class GptRepository {
    private val service = NetworkClient.RetrofitClient.getClient().create(ApiService::class.java)
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