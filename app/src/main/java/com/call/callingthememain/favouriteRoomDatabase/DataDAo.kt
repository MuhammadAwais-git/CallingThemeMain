package com.call.callingthememain.favouriteRoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.call.callingthememain.models.FavCallThemeModel


@Dao
interface DataDAo {
    @Query("SELECT * FROM Data_Location")
    fun getAll(): LiveData<List<FavCallThemeModel>>

    @Insert
    fun insert(PName: FavCallThemeModel)

    @Query("SELECT * FROM Data_Location WHERE id = :id")
    fun isFavorite(id: Int): LiveData<FavCallThemeModel>

    @Delete
    fun delete(PName: FavCallThemeModel)

}