package com.valify.popflake.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)