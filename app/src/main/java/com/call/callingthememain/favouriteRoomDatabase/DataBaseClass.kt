package com.call.callingthememain.favouriteRoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.call.callingthememain.models.FavCallThemeModel

@Database(entities = [FavCallThemeModel::class], version = 1)
abstract class DataBaseClass : RoomDatabase() {

    abstract fun Dao(): DataDAo


    companion object {

        private var instance: DataBaseClass? = null

        @Synchronized
        fun getInstance(ctx: Context): DataBaseClass {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext, DataBaseClass::class.java,
                    "Data_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance!!
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: DataBaseClass) {
            val noteDao = db.Dao()

        }
    }
}



