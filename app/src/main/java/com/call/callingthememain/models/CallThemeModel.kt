package com.call.callingthememain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CallThemeModel(
    var id: Int? = null,
    var Img: Int? = null,
    var video: Int? = null,
    var position: Int? = null
):Parcelable
