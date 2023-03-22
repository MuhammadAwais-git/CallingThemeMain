package com.call.callingthememain.utils

import com.call.callingthememain.R
import com.call.callingthememain.models.CallThemeModel


class AllDataCallTheme {
    companion object{


        fun trendingDataTheme():ArrayList<CallThemeModel>{
            val myListData: ArrayList<CallThemeModel> = ArrayList()

            myListData.add(CallThemeModel(1,R.raw.video1, R.raw.video1, 1))
            myListData.add(CallThemeModel(2,R.raw.video4, R.raw.video4, 1))
            myListData.add(CallThemeModel(3,R.raw.video11, R.raw.video11, 1))
            myListData.add(CallThemeModel(4,R.raw.video12, R.raw.video12, 1))

            myListData.add(CallThemeModel(5,R.drawable.img1,null,0))
            myListData.add(CallThemeModel(6,R.drawable.img2,null,0))
            myListData.add(CallThemeModel(7,R.drawable.img3,null,0))
            myListData.add(CallThemeModel(8,R.drawable.img4,null,0))
            myListData.add(CallThemeModel(9,R.drawable.img5,null,0))
            myListData.add(CallThemeModel(10,R.drawable.img6,null,0))
            myListData.add(CallThemeModel(11,R.drawable.img7,null,0))
            myListData.add(CallThemeModel(12,R.drawable.img8,null,0))
            myListData.add(CallThemeModel(13,R.drawable.img9,null,0))
            myListData.add(CallThemeModel(14,R.drawable.img10,null,0))

        return myListData
        }

        fun motionDataTheme():ArrayList<CallThemeModel>{
            val myListData: ArrayList<CallThemeModel> = ArrayList()

            myListData.add(CallThemeModel(21,R.raw.video2, R.raw.video2, 1))
            myListData.add(CallThemeModel(22,R.raw.video3, R.raw.video3, 1))
            myListData.add(CallThemeModel(23,R.raw.video5, R.raw.video5, 1))
            myListData.add(CallThemeModel(24,R.raw.video6, R.raw.video6, 1))
            myListData.add(CallThemeModel(25,R.raw.video7, R.raw.video7, 1))
            myListData.add(CallThemeModel(26,R.raw.video8, R.raw.video8, 1))
            myListData.add(CallThemeModel(27,R.raw.video9, R.raw.video9, 1))
            myListData.add(CallThemeModel(28,R.raw.video10, R.raw.video10, 1))

            return myListData
        }

        fun popularDataTheme():ArrayList<CallThemeModel>{
            val myListData: ArrayList<CallThemeModel> = ArrayList()
            myListData.add(CallThemeModel(41,R.drawable.img11,null,0))
            myListData.add(CallThemeModel(42,R.drawable.img12,null,0))
            myListData.add(CallThemeModel(43,R.drawable.img13,null,0))
            myListData.add(CallThemeModel(44,R.drawable.img14,null,0))
            myListData.add(CallThemeModel(45,R.drawable.img15,null,0))
            myListData.add(CallThemeModel(46,R.drawable.img16,null,0))
            myListData.add(CallThemeModel(47,R.drawable.img17,null,0))
            myListData.add(CallThemeModel(48,R.drawable.img18,null,0))
            myListData.add(CallThemeModel(49,R.drawable.img19,null,0))
            myListData.add(CallThemeModel(50,R.drawable.img20,null,0))
            return myListData
        }
    }
}