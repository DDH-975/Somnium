package com.project.somnium.data.remote

data class ApiDataModel(
     val created : Long,
     val data : List<Url>
)
data class Url(
     //이미지 url
     val url : String
)