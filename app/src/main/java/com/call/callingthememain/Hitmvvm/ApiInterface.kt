package com.bgRemover.backgroundremover.MVVM

import com.call.callingthememain.Hitmvvm.Model.pixabayModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
@GET("api/")
    fun getAllPic(
        @Query("key") key: String ="29643680-cc329d3bf258e0ee9013ba1bc",
        @Query("q") query: String,
        @Query("image_type") type: String = "photo",
        @Query("per_page") per_page: String="30",
        @Query("safesearch") safesearch: String="true"
): Call<pixabayModel>
}

