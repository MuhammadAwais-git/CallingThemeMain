package com.call.callingthememain.fragments

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.R
import com.call.callingthememain.databinding.FragmentPerviewWallpaperBinding
import com.call.callingthememain.utils.Helper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PerviewWallpaperFragment : Fragment() {
    private lateinit var binding: FragmentPerviewWallpaperBinding
    private lateinit var model: Hit

    private val args: PerviewWallpaperFragmentArgs by navArgs()

    private lateinit var mContext: Context
    var bitmap: Bitmap? = null
companion object{
    lateinit var progressBar:ProgressBar
}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerviewWallpaperBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.wallpaperModel.let {
            model = it
            /*  Glide.with(mContext)
                .load(it.largeImageURL)
                .into(binding.imgWallpaperPerview)*/
            binding.perviewItemProgressbar.visibility = View.VISIBLE
            Glide.with(mContext).load(model.largeImageURL)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.perviewItemProgressbar.visibility = View.GONE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.perviewItemProgressbar.visibility = View.GONE

                        return false
                    }
                })
                .into(binding.imgWallpaperPerview)

        }
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardSetWallpaper.setOnClickListener {
//            dialog!!.show()
            bitmap = getBitmapFromView(binding.imgWallpaperPerview)
            /*  val dialog = WallpaperDialog(mContext, bitmap!!)
              dialog.show()*/

            Create_dialog_for_Set_Wallpaper()

//            setwallpaperDialog(model)
//            saveImageToCache(mContext, model.largeImageURL)
        }
        fun saveToCacheMemory(context: Context, bitmapImage: Bitmap): String? {
            Log.d("TAG", "saveToCacheMemory: ")
            val mDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
            val cw = ContextWrapper(context)
            val directory = cw.getDir("images", Context.MODE_PRIVATE)
            // Create imageDir
            val mypath = File(directory, mDateFormat.format(Date()))
            Log.d("TAG", "directory: $directory")
            var fos: FileOutputStream? = null
            try {
//            fos = FileOutputStream(File(mypath.absolutePath), true)

                fos = FileOutputStream(mypath)
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
                Log.d("TAG", "bit exception: Success")
            } catch (e: Exception) {
                Log.d("TAG", "bit exception: " + e.message)
                e.printStackTrace()
            } finally {
                try {
                    fos!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d("TAG", "io exce: " + e.message)
                }
            }
            Log.d("TAG", "absolute path " + directory.absolutePath)
            return mypath.absolutePath
        }


    }

    private fun Create_dialog_for_Set_Wallpaper() {
        val mBuilder = AlertDialog.Builder(mContext)
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.wallpaer_dialog, null, false)


        mBuilder.setView(view)
        var dialog: AlertDialog? = null

        var builder: AlertDialog.Builder? = null
        dialog = mBuilder.create()

        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnHomeScreen: TextView = view.findViewById(R.id.btnHomeScreen)
        val btnLockScreen: TextView = view.findViewById(R.id.btnLockScreen)
        val btnBothScreen: TextView = view.findViewById(R.id.btnBothScreen)

        progressBar = view.findViewById(R.id.ProgressBardialog)
        btnHomeScreen.setOnClickListener {

            progressBar.visibility=View.VISIBLE
            Helper.setAsWallpaperHome(bitmap!!, mContext)
            dialog.dismiss()

        }
        btnLockScreen.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            Helper.setAsWallpaperlock(bitmap!!, mContext)
            dialog.dismiss()
        }
        btnBothScreen.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            Helper.setBoth(mContext, bitmap!!)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun getBitmapFromView(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

}