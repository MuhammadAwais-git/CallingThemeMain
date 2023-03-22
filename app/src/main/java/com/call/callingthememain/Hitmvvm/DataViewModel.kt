package com.call.callingthememain.Hitmvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.http.Query
import java.io.File

class DataViewModel(private val repository: MainRepository): ViewModel() {
     val mLiveData = repository.mMuteableData

    fun getData(query: String){
        Log.d("getData","getData45454     $query")
        repository.getImgdata(query)
    }

}