package com.call.callingthememain.favouriteRoomDatabase

import android.app.Application
import androidx.lifecycle.LiveData
import com.call.callingthememain.models.FavCallThemeModel


class DataRepository(application: Application) {

    private var dataDao: DataDAo
    private var allData: LiveData<List<FavCallThemeModel>>

    private val database = DataBaseClass.getInstance(application)

    init {
        dataDao = database.Dao()
        allData = dataDao.getAll()
    }

    fun insert(data: FavCallThemeModel) {
        dataDao.insert(data)

    }
    fun isFavourite(data: Int?):LiveData<FavCallThemeModel> {
        return dataDao.isFavorite(data!!)
    }

    fun delete(data: FavCallThemeModel) {
        dataDao.delete(data)

    }


    fun getAllData(): LiveData<List<FavCallThemeModel>> {
        return allData
    }



}