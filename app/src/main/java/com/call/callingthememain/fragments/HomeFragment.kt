package com.call.callingthememain.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.call.callingthememain.Adapters.ViewPagerAdapter
import com.call.callingthememain.R
import com.call.callingthememain.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var sharedPreference: SharedPreferences
    lateinit var binding: FragmentHomeBinding
    lateinit var videoView: VideoView
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        Log.d("zzzzzzzzzz  HomeFragment", "onAttach ")

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("zzzzzzzzzz  HomeFragment", "onCreate:onCreateViewHomeFragment ")
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("zzzzzzzzzz  HomeFragment", "onCreate:onViewCreatedHomeFragment ")
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(mContext, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)



        /*       //video

               val mediaController = MediaController(mContext)
               mediaController.setAnchorView(videoView)
               val videoUrl =
                   "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"

       //        val uri = Uri.parse(Environment.getExternalStorageDirectory().path + "/Download/video.mp4")
               val uri = Uri.parse(videoUrl)

               videoView.setMediaController(mediaController)
               videoView.setVideoURI(uri)
               videoView.requestFocus()
               videoView.start()
               videoView.setMediaController(null)
               videoView.setOnCompletionListener {

                   videoView.seekTo(0)
                   videoView.start()
               }
       */

        /*   binding.imageBackground.setOnClickListener {

               val file = File(mContext.filesDir, "my_image1.jpg")
               val inputStream: InputStream = this.resources.openRawResource(R.drawable.drawer_main_icon)
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
               Log.d("zzzzzzzzzz  HomeFragment", "clicklistener: filePath $filePath")

               sharedPreference = mContext.getSharedPreferences(mContext.packageName, Context.MODE_PRIVATE)
               val editor = sharedPreference.edit()
               editor.putString("ThemePath", file.path)
               editor.commit()

               Toast.makeText(mContext, "apply Img", Toast.LENGTH_SHORT).show()
           }*/

    }

    override fun onDestroyView() {
        Log.d("zzzzzzzzzz  HomeFragment", "onDestroyView")
        super.onDestroyView()
    }


}