package com.call.callingthememain.Hitmvvm.Model


import com.google.gson.annotations.SerializedName

data class pixabayModel(
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("total")
    val total: Int, // 21456
    @SerializedName("totalHits")
    val totalHits: Int // 500
)