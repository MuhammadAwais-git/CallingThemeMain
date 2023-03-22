package com.call.callingthememain.viewpagerFragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.call.callingthememain.R
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.models.FavCallThemeModel
import kotlinx.android.synthetic.main.fragment_preview_calltheme.*
import kotlinx.android.synthetic.main.fragment_preview_favourite.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.ArrayList


class PreviewFavouriteFragment : Fragment() {

    private lateinit var mContext: Context
    private val args:PreviewFavouriteFragmentArgs by navArgs()
    private lateinit var model: FavCallThemeModel
    var sharedpreferences: SharedPreferences? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedpreferences = mContext.getSharedPreferences(mContext.packageName, Context.MODE_PRIVATE)


        back_arrowf.setOnClickListener {
            findNavController().popBackStack()
        }

        args.favCallThemeModel.let {
            model=it
            when (it.position) {
                0 -> {
                    imgFavPreview.visibility = View.VISIBLE
                   previewFavVideoView.visibility = View.GONE

                    Glide.with(mContext)
                        .load(it.Img)
                        .into(imgFavPreview)

                    txtSetCallThemeFav.setOnClickListener {

                        val file = File(mContext.filesDir, "${model.Img}")
                        val inputStream: InputStream = this.resources.openRawResource(model.Img!!)
                        val outputStream = FileOutputStream(file)
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (inputStream.read(buffer).also { length = it } > 0) {
                            outputStream.write(buffer, 0, length)
                        }
                        outputStream.flush()
                        outputStream.close()
                        inputStream.close()
                        val filePath = file.path


                        val editor = sharedpreferences!!.edit()
                        editor.putString("ThemePath", file.path)
                        editor.putInt("ThemeType", 0)
                        editor.apply()

                        Toast.makeText(mContext, "Applied Theme", Toast.LENGTH_SHORT).show()

//            recentListData.add(model.Img!!)
                    }

                }
                1 -> {
                    imgFavPreview.visibility = View.GONE
                    previewFavVideoView.visibility = View.VISIBLE
                    //video

                    val mediaController = MediaController(mContext)
                    mediaController.setAnchorView(previewFavVideoView)
                    val uri =
                        Uri.parse("android.resource://" + mContext.packageName.toString() + "/" + model.videos)
                    previewFavVideoView.setMediaController(mediaController)
                    previewFavVideoView.setVideoURI(uri)
                    previewFavVideoView.requestFocus()
                    previewFavVideoView.start()
                    previewFavVideoView.setMediaController(null)
                    previewFavVideoView.setOnCompletionListener {

                        previewFavVideoView.seekTo(0)
                        previewFavVideoView.start()
                    }

                    txtSetCallThemeFav.setOnClickListener {

                        val file = File(mContext.filesDir, "${model.videos}")
                        val inputStream: InputStream = this.resources.openRawResource(model.videos!!)
                        val outputStream = FileOutputStream(file)
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (inputStream.read(buffer).also { length = it } > 0) {
                            outputStream.write(buffer, 0, length)
                        }
                        outputStream.flush()
                        outputStream.close()
                        inputStream.close()
                        val filePath = file.path
                        val editor = sharedpreferences!!.edit()
                        editor.putString("ThemePath", file.path)
                        editor.putInt("ThemeType", 1)
                        editor.apply()

                        Toast.makeText(mContext, "Applied Theme", Toast.LENGTH_SHORT).show()

//            recentListData.add(model.Img!!)
                    }


                }
                else -> {
                }
            }
        }
    }


}