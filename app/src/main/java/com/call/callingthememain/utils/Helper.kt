package com.call.callingthememain.utils

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Context.WALLPAPER_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.StrictMode
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.fragments.PerviewWallpaperFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class Helper (){
    companion object{


        var stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)

        fun bytesToHuman(size: Long, withUnit: Int): String {
            var totalSize = "0"
            var sizeUnit = "KB"
            val mKb: Long = 1024
            val mMb = mKb * 1024
            val mGb = mMb * 1024
            val mTb = mGb * 1024
            val mPb = mTb * 1024
            val mEb = mPb * 1024
            if (size in mKb..mMb) {
                totalSize = DecimalFormat("#.##").format(size.toDouble() / mKb)
                sizeUnit = "KB"
                // totalSize= floatForm(size.toDouble() / mKb).toString() + " KB"
            }
            if (size in mMb until mGb) {
                totalSize = DecimalFormat("#.##").format(size.toDouble() / mMb).toString()
                sizeUnit = "MB"
            }
            if (size in mGb until mTb) {
                totalSize = DecimalFormat("#.##").format(size.toDouble() / mGb).toString()
                sizeUnit = "GB"
            }
            if (size in mTb until mPb) {
                totalSize = DecimalFormat("#.##").format(size.toDouble() / mTb).toString()
                sizeUnit = "TB"
            }
            if (size in mPb until mEb) {
                totalSize = DecimalFormat("#.##").format(size.toDouble() / mPb).toString()
                sizeUnit = "PB"
            }
            if (size >= mEb) {
                DecimalFormat("#.##").format(size.toDouble() / mEb).toString()
                sizeUnit = "EB"
            }

            return if (withUnit == 1) {
                "$totalSize $sizeUnit"
            } else {
                "$totalSize "
            }
        }



        fun getTotalUSedStorageSize(type:Int): Long {
            val iPath: File = Environment.getDataDirectory()
            val iStat = StatFs(iPath.path)
            val iBlockSize = iStat.blockSizeLong
            val iAvailableBlocks = iStat.availableBlocksLong
            val iTotalBlocks = iStat.blockCountLong
//            val iAvailableSpace = bytesToHuman(iAvailableBlocks * iBlockSize, 0)
            val iAvailableSpace = iAvailableBlocks * iBlockSize

            val iTotalSpace = bytesToHuman(iTotalBlocks * iBlockSize, 0)
            return  iAvailableSpace
//            when(type){
//                1->{
////                    (iTotalSpace.toLong() / (AppCoolerConstants.deviceTotalSize.toLong())) * 100
//                }
//                2->{
//                    (iAvailableSpace / (AppCoolerConstants.deviceTotalSize.toLong())) * 100
//                }
//                3->{
//                    iAvailableSpace
//                }
//                4->{
//                    (iTotalBlocks * iBlockSize) - iAvailableSpace
//                }
//                else -> {
//                    (iTotalSpace.toLong() / (AppCoolerConstants.deviceTotalSize.toLong())) * 100
//                }
//            }
        }

        private fun bytesToHumanHome(size: Long): String {
            var totalSize = "0"
            var sizeUnit = "KB"
            val mKb: Long = 1024
            val mMb = mKb * 1024
            val mGb = mMb * 1024
            val mTb = mGb * 1024
            val mPb = mTb * 1024
            val mEb = mPb * 1024
            if (size in mKb..mMb) {
                totalSize = DecimalFormat("#").format(size.toDouble() / mKb)

            }
            if (size in mMb until mGb) {
                totalSize = DecimalFormat("#").format(size.toDouble() / mMb).toString()
            }
            if (size in mGb until mTb) {
                totalSize = DecimalFormat("#").format(size.toDouble() / mGb).toString()
            }
            if (size in mTb until mPb) {
                totalSize = DecimalFormat("#").format(size.toDouble() / mTb).toString()
            }
            if (size in mPb until mEb) {
                totalSize = DecimalFormat("#").format(size.toDouble() / mPb).toString()
            }
            if (size >= mEb) {
                DecimalFormat("#").format(size.toDouble() / mEb).toString()
            }
            return totalSize
        }


        private fun getInternalTotalSpace(): Long {
            return stat.blockSizeLong * stat.blockCountLong
        }

        fun getTotalDirectorySize(unitType:Int):String{
            var mTotalSizeStorage = "2GB"
            var sizeUnit = "KB"

            val convertBytes: String = bytesToHumanHome(getInternalTotalSpace())

//            val delimeter = " "
//            val part = convertBytes.split(delimeter)
//            val round = part[0].toInt()
            val round = convertBytes.toInt()
//            val round = convertBytes.substring(0, convertBytes.length + -6).toInt().toFloat().roundToInt()
            if (round in 2..4) {
                mTotalSizeStorage = "4"
                sizeUnit = "GB"
            } else if (round in 5..8) {
                mTotalSizeStorage = "8"
                sizeUnit = "GB"
            } else if (round in 9..15) {
                mTotalSizeStorage = "16"
                sizeUnit = "GB"
            } else if (round in 17..31) {
                mTotalSizeStorage = "32"
                sizeUnit = "GB"
            } else if (round in 33..63) {
                mTotalSizeStorage = "64"
                sizeUnit = "GB"
            } else if (round in 65..127) {
                mTotalSizeStorage = "128"
                sizeUnit = "GB"
            } else if (round in 129..255) {
                mTotalSizeStorage = "256"
                sizeUnit = "GB"
            } else if (round <= 256 || round >= 512) {
                mTotalSizeStorage = "2"
                sizeUnit = "GB"
            } else {
                mTotalSizeStorage = "512"
                sizeUnit = "GB"
            }
//            return mTotalSizeStorage

            return if (unitType ==1){
                "$mTotalSizeStorage $sizeUnit"
            }else{
                mTotalSizeStorage
            }
        }

        fun saveMediaToStorage(mContext:Context,bitmap: Bitmap) {

            val filename = "${System.currentTimeMillis()}.png"
            var fos: OutputStream? = null
            var DirectoryNameForFacebook = "/CallingThemeGt/"

            val DirectoryFacebookShow = File(
                Environment.getExternalStorageDirectory().toString() + "/Pictures/CallingThemeGt"
            )
            if (!DirectoryFacebookShow.exists()) {
                DirectoryFacebookShow.mkdirs()
            }
            //For devices running android >= Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //getting the contentResolver
                mContext.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        //putting file information in content values
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+DirectoryNameForFacebook)
                    }

                    //Inserting the contentValues to contentResolver and getting the Uri
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    //Opening an outputstream with the Uri that we got
                    fos = imageUri?.let { resolver.openOutputStream(it) }

                }
            } else {
                //These for devices running on android < Q
                //So I don't think an explanation is needed here
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+DirectoryNameForFacebook)
                val image = File(imagesDir, filename)
                fos = FileOutputStream(image)

            }
            fos?.use {
                //Finally writing the bitmap to the output stream that we opened
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, it)
            }
        }

        fun getBitmapFromURL(src: String?): Bitmap? {
            return try {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                val url = URL(src)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        fun setWallpaperHome(bitmap: Bitmap, context: Context) {
            Log.d("TAG", "setWallpaperHome:  aya")

            val wallpaperManager = WallpaperManager.getInstance(context)
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            try {
                wallpaperManager.setBitmap(bitmap)
                wallpaperManager.setWallpaperOffsetSteps(1F, 1F)
                wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        fun setAsWallpaperlock(bitmap: Bitmap, context: Context) {
            Log.d("TAG", "setAsWallpaperlock:  aya")
            val wallpaperManager =
                context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
            Glide.with(context).asBitmap()
                .load(bitmap)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {

                        val myBitmap = Bitmap.createScaledBitmap(
                            resource,
                            wallpaperManager.desiredMinimumWidth,
                            wallpaperManager.desiredMinimumHeight,
                            true
                        )
                        wallpaperManager.suggestDesiredDimensions(
                            wallpaperManager.desiredMinimumWidth,
                            wallpaperManager.desiredMinimumHeight
                        )
                        try {
//                    wallpaperManager.setBitmap(myBitmap)
//                        wallpaperManager.setBitmap(myBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                            wallpaperManager.setBitmap(
                                myBitmap,
                                null,
                                false,
                                WallpaperManager.FLAG_LOCK
                            );
                            Toast.makeText(
                                context, "Wallpaper set succesfully", Toast.LENGTH_SHORT
                            ).show()
                            PerviewWallpaperFragment.progressBar.visibility=View.GONE
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(context, "Wallpaper not set", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
        }
        fun setAsWallpaperHome(bitmap: Bitmap, context: Context) {
            Log.d("TAG", "setAsWallpaperHome:  aya")

            val wallpaperManager =
                context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
            Glide.with(context).asBitmap()
                .load(bitmap)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {
                        val myBitmap = Bitmap.createScaledBitmap(
                            resource,
                            wallpaperManager.desiredMinimumWidth,
                            wallpaperManager.desiredMinimumHeight,
                            true
                        )
                        wallpaperManager.suggestDesiredDimensions(
                            wallpaperManager.desiredMinimumWidth,
                            wallpaperManager.desiredMinimumHeight
                        )
                        try {
//                    wallpaperManager.setBitmap(myBitmap)
                            wallpaperManager.setBitmap(
                                myBitmap,
                                null,
                                true,
                                WallpaperManager.FLAG_SYSTEM
                            )
//                    wallpaperManager.setBitmap(myBitmap, null, false, WallpaperManager.FLAG_LOCK);
                            Toast.makeText(
                                context, "Wallpaper set succesfully", Toast.LENGTH_SHORT
                            ).show()
                            PerviewWallpaperFragment.progressBar.visibility=View.GONE
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(context, "Wallpaper not set", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                })
        }
        fun saveImageToCache(context: Context, imageUrl: String) {
            CoroutineScope(Dispatchers.IO).launch {


                val fileName = "myImage.jpg"
                val cacheDir = context.cacheDir
                val file = File(cacheDir, fileName)
                Log.d("TAG", "saveImageToCache: ${file.absolutePath}")
                val url = URL(imageUrl)

                Log.d("TAG", "saveImageToCache: $url")
                val bitmap: Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())


                val outputStream = FileOutputStream(file)
                Log.d("TAG", "saveImageToCache: ${outputStream.channel}")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

                outputStream.flush()
                outputStream.close()

            }
        }

         fun setHomeScreen(context: Context,bitmap: Bitmap) {
             Log.d("TAG", "setHomeScreen:setHomeScreen ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //  bitmap = BitmapFactory.decodeResource(getResources(), imagePath!!.toInt());
                val manager = WallpaperManager.getInstance(context)
                try {
                    manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    Toast.makeText(context, "Wallpaper has been set", Toast.LENGTH_SHORT).show()

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            } else {
                val manager = WallpaperManager.getInstance(context)
                val mWidth = manager.desiredMinimumWidth
                val mHeight = manager.desiredMinimumHeight
                val matrix = Matrix()
                //val bitmap = BitmapFactory.decodeResource(resources, imagePath!!.toInt())
                matrix.setScale(
                    mWidth.toFloat() / bitmap.width.toFloat(),
                    mHeight.toFloat() / bitmap.height.toFloat())
                val scaledbitmap =
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                try {
                    manager.setBitmap(scaledbitmap)
                    Toast.makeText(context, "Wallpaper has been set", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        fun setLockScreen(context: Context,bitmap: Bitmap) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // val bitmap = BitmapFactory.decodeResource(resources, imagePath!!.toInt())
                val manager = WallpaperManager.getInstance(context)
                try {
                    manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    Toast.makeText(context, "Wallpaper Updated", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Your device is not support for Lock screen", Toast.LENGTH_SHORT).show()
            }
        }

        fun setBoth(context: Context, bitmap: Bitmap) {
            val manager = context.getSystemService(WALLPAPER_SERVICE) as WallpaperManager
            manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            Toast.makeText(context, "Wallpaper Updated", Toast.LENGTH_SHORT).show()
            PerviewWallpaperFragment.progressBar.visibility=View.GONE

        }


    }

}