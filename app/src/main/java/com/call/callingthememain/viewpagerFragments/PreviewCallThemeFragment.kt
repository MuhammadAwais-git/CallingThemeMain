package com.call.callingthememain.viewpagerFragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.call.callingthememain.MainActivity
import com.call.callingthememain.R
import com.call.callingthememain.databinding.FragmentPreviewCallthemeBinding
import com.call.callingthememain.favouriteRoomDatabase.DataViewModel
import com.call.callingthememain.fragments.StorageFragment
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.models.FavCallThemeModel
import kotlinx.android.synthetic.main.fragment_preview_calltheme.*
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class PreviewCallThemeFragment : Fragment() {
    lateinit var mContext: Context
    var sharedpreferences: SharedPreferences? = null
    private val args: PreviewCallThemeFragmentArgs by navArgs()
    private lateinit var model: CallThemeModel

    private lateinit var binding: FragmentPreviewCallthemeBinding
    var isFavourite: Boolean = false

    var chkPreferences: Int? = null

    private lateinit var vm: DataViewModel


    companion object {
        val recentListData = ArrayList<Int>()
        val favListData = ArrayList<FavCallThemeModel>()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreviewCallthemeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sharedpreferences = mContext.getSharedPreferences(mContext.packageName, Context.MODE_PRIVATE)


        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        vm = ViewModelProvider(this)[DataViewModel::class.java]

        args.callThemeModel.let {
            model = it

            when (it.position) {
                0 -> {
                    binding.imgWallpaperPerview.visibility = View.VISIBLE
                    binding.previewVideoView.visibility = View.GONE

                    Glide.with(mContext)
                        .load(it.Img)
                        .into(binding.imgWallpaperPerview)

                    binding.txtSetCallTheme.setOnClickListener {

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
                    binding.imgWallpaperPerview.visibility = View.GONE
                    binding.previewVideoView.visibility = View.VISIBLE
                    //video

                    val mediaController = MediaController(mContext)
                    mediaController.setAnchorView(previewVideoView)
                    val uri =
                        Uri.parse("android.resource://" + mContext.packageName.toString() + "/" + it.video)
                    previewVideoView.setMediaController(mediaController)
                    previewVideoView.setVideoURI(uri)
                    previewVideoView.requestFocus()
                    previewVideoView.start()
                    previewVideoView.setMediaController(null)
                    previewVideoView.setOnCompletionListener {

                        previewVideoView.seekTo(0)
                        previewVideoView.start()
                    }

                    binding.txtSetCallTheme.setOnClickListener {

                        val file = File(mContext.filesDir, "${model.video}")
                        val inputStream: InputStream = this.resources.openRawResource(model.video!!)
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

        vm.isFavourite(model.id).observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.imgFav.setColorFilter(mContext.resources.getColor(R.color.red_button))
                isFavourite=true
            }else{
                binding.imgFav.setColorFilter(mContext.resources.getColor(R.color.white))
                isFavourite=false
            }

        })

        binding.imgFav.setOnClickListener {

            if (isFavourite){
                vm.delete(FavCallThemeModel(model.id, model.Img, model.video,model.position))
            }else{
                vm.insert(FavCallThemeModel(model.id, model.Img!!, model.video,model.position))
            }

          /*  Log.d("click", "chk is pahle " + isFavourite)
            if (!isFavourite!!) {


                binding.imgFav.setColorFilter(mContext.resources.getColor(R.color.red_button))
                vm.insert(FavCallThemeModel(null, model.Img!!, model.video))
                isFavourite = true

                if (MainActivity.fragmentName == "TrendingFragment") {
                    editor.putInt("favPos", TrendingFragment.pos)
                    editor.apply()
                    Log.d("xhkpref", "putInt" + TrendingFragment.pos)

                } else if (MainActivity.fragmentName == "MotionFragment") {
                    editor.putInt("favPos", MotionFragment.pos)
                    editor.apply()
                    Log.d("xhkpref", "putInt" + MotionFragment.pos)

                } else if (MainActivity.fragmentName == "PopularFragment") {
                    editor.putInt("favPos", PopularFragment.pos)
                    editor.apply()
                    Log.d("xhkpref", "putInt" + PopularFragment.pos)

                }

            } else {


                binding.imgFav.setColorFilter(mContext.resources.getColor(R.color.white))
//                vm.delete(StorageFragment.favThemeList[StorageFragment.listPosition])
                vm.delete(FavCallThemeModel(null, model.Img, model.video))
                Log.d("click", "delete " + isFavourite)
                Log.d("TAG", "onViewCreated:  listPosition121 ${StorageFragment.listPosition} ")
//                    mList.removeAt(position)
//                madapter.notifyItemChanged(position)
                Toast.makeText(
                    mContext,
                    "Removed Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                isFavourite = false

                editor.putInt("favPos", 0)
                editor.apply()
//                prfs.edit().clear().commit()
            }*/


        }

    }

    override fun onResume() {
        args.callThemeModel.let {
            model = it

            when (it.position) {
                0 -> {
                    binding.imgWallpaperPerview.visibility = View.VISIBLE
                    binding.previewVideoView.visibility = View.GONE

                    Glide.with(mContext)
                        .load(it.Img)
                        .into(binding.imgWallpaperPerview)

                    binding.txtSetCallTheme.setOnClickListener {

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
                    binding.imgWallpaperPerview.visibility = View.GONE
                    binding.previewVideoView.visibility = View.VISIBLE
                    //video

                    val mediaController = MediaController(mContext)
                    mediaController.setAnchorView(previewVideoView)
                    val uri =
                        Uri.parse("android.resource://" + mContext.packageName.toString() + "/" + it.video)
                    previewVideoView.setMediaController(mediaController)
                    previewVideoView.setVideoURI(uri)
                    previewVideoView.requestFocus()
                    previewVideoView.start()
                    previewVideoView.setMediaController(null)
                    previewVideoView.setOnCompletionListener {

                        previewVideoView.seekTo(0)
                        previewVideoView.start()
                    }

                    binding.txtSetCallTheme.setOnClickListener {

                        val file = File(mContext.filesDir, "${model.video}")
                        val inputStream: InputStream = this.resources.openRawResource(model.video!!)
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
        super.onResume()
    }
}