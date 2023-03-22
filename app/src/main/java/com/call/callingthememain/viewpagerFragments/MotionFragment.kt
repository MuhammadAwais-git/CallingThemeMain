package com.call.callingthememain.viewpagerFragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.call.callingthememain.Adapters.CallThemeAdapter
import com.call.callingthememain.R
import com.call.callingthememain.fragments.HomeFragmentDirections
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.utils.AllDataCallTheme
import kotlinx.android.synthetic.main.fragment_motion.*
import java.util.ArrayList


class MotionFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var videoView: VideoView
    private var madapter: CallThemeAdapter? = null

    var myListData: ArrayList<CallThemeModel>? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        Log.d("zzzzzzzzzz  HomeFragment", "onAttach ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_motion, container, false)
//        videoView = view.findViewById<View>(R.id.videoView) as VideoView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCallThemeAdapter()


    }


    private fun setCallThemeAdapter() {
        myListData = ArrayList()
        myListData= AllDataCallTheme.motionDataTheme()


        madapter = CallThemeAdapter(
            mContext,
            myListData!!,
            object : CallThemeAdapter.OnItemClickListener {
                override fun onItemClick(model: CallThemeModel) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToPreviewCallThemeFragment(model)
                    findNavController().navigate(action)

                }
                })
        motionRecycler.apply {
            motionRecycler.layoutManager = GridLayoutManager(mContext, 2)
            motionRecycler.adapter = madapter
        }
    }



}