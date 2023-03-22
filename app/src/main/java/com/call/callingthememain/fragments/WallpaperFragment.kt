package com.call.callingthememain.fragments

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.call.callingthememain.Adapters.PixabayAdapter
import com.call.callingthememain.Hitmvvm.DataViewModel
import com.call.callingthememain.Hitmvvm.MainRepository
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.Hitmvvm.MyViewModelFactory
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wallpaper.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class WallpaperFragment : Fragment() {
    private lateinit var viewModel: DataViewModel
    var madapter: PixabayAdapter? = null
    private var mContext: Context? = null
    var mWallpaperList: ArrayList<Hit>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallpaper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerPixaBay()

    }
    private fun observerPixaBay() {

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository()))[DataViewModel::class.java]
        viewModel.getData("wallpaper")
        viewModel.mLiveData.observe(viewLifecycleOwner, Observer { mList ->
            Log.d("TAG", "onCreate: list ${mList.hits}")

            mWallpaperList = mList.hits as ArrayList<Hit>
            setpixabayAdapter(mWallpaperList!!)
        })
    }

    private fun setpixabayAdapter(imgListPixaBay: ArrayList<Hit>) {

        madapter =
            PixabayAdapter(mContext!!, imgListPixaBay, object : PixabayAdapter.OnItemClickListener {
                override fun onItemClick(model: Hit) {
                    val action = WallpaperFragmentDirections.actionWallpaperFragmentToPerviewWallpaperFragment(model)
                    findNavController().navigate(action)

                }

            })
        wallpaperRecycler.apply {
            wallpaperRecycler.layoutManager = GridLayoutManager(mContext, 2)
            wallpaperRecycler.adapter = madapter
        }
    }



}