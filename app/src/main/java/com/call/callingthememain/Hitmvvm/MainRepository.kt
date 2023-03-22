package com.call.callingthememain.Hitmvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.call.callingthememain.Hitmvvm.Model.pixabayModel
import retrofit2.Call
import retrofit2.Response

class MainRepository  {

    val mMuteableData = MutableLiveData<pixabayModel>()

    val key = "29643680-cc329d3bf258e0ee9013ba1bc"
    val image_type:String="photo"


    fun getImgdata(q: String){
        RetrofitInstance.api.getAllPic(key,q,image_type).enqueue(object :
            retrofit2.Callback<pixabayModel> {
            override fun onResponse(call: Call<pixabayModel>, response: Response<pixabayModel>) {

                Log.d("TAG", "onCreate: onResponsedata pixabay")

                if (response.body() != null) {
                    mMuteableData.postValue(response.body())

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<pixabayModel>, t: Throwable) {
                Log.d("TAG", "onFailure" + t.message.toString())
                Log.d("observeLiveData", "=================109  ")
            }
        })

    }
}