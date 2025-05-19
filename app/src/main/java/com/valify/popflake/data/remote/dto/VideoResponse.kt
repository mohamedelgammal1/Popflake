package com.valify.popflake.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val id: Int,
    val results: List<Video>
)

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    @SerializedName("published_at") val publishedAt: String
)