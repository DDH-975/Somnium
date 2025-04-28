package com.project.somnium.retrofit

import android.util.Log
import com.project.somnium.BuildConfig
import com.project.somnium.makeImg_Recycler.RecyclerDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GptRequest(val prompt: String, val callback: ApiCallback) {
    private val service = NetworkClient.RetrofitClient.getClient().create(ApiService::class.java)
    private val apikey = "Bearer ${BuildConfig.Apikey}"


    //콜백 함수
    interface ApiCallback {
        fun onDataReceived(data: RecyclerDataModel)
    }


    //api 요청
    fun gptRequest() {
        val request = ImageRequestBody(prompt)
        val call = service.postGptApiImage(apikey, request)

        call?.enqueue(object : Callback<ApiDataModel?> {
            override fun onResponse(call: Call<ApiDataModel?>, response: Response<ApiDataModel?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val url = result?.data?.get(0)?.url.toString()
                    val data = RecyclerDataModel(prompt, url)

                    //콜백
                    callback.onDataReceived(data)
                }
            }

            override fun onFailure(call: Call<ApiDataModel?>, t: Throwable) {
                Log.e("onFailure", "api 요청에 실패함 ${t.stackTrace}")
            }
        })
    }

}