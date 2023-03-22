package com.call.callingthememain.favouriteRoomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.call.callingthememain.models.FavCallThemeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = DataRepository(app)
    private val allNotes = repository.getAllData()

    fun insert(data: FavCallThemeModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(data)
        }
    }

    fun isFavourite(data: Int?):LiveData<FavCallThemeModel> {
            return repository.isFavourite(data)
    }
    fun delete(data: FavCallThemeModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(data)
        }
    }

    fun getAllNotes(): LiveData<List<FavCallThemeModel>> {
        return allNotes
    }


}
