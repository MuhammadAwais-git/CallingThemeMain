package com.call.callingthememain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Data_Location")
data class FavCallThemeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "img")
    val Img: Int?,
    @ColumnInfo(name = "videos")
    val videos: Int?,
    @ColumnInfo(name = "position")
    val position: Int?,
    ):Parcelable