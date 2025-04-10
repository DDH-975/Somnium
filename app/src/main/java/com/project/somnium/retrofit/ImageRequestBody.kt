package com.project.somnium.retrofit

data class ImageRequestBody(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024"
)
