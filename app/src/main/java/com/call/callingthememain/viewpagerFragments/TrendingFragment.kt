package com.call.callingthememain.viewpagerFragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.call.callingthememain.Adapters.CallThemeAdapter
import com.call.callingthememain.Adapters.MobileToolsAdapter
import com.call.callingthememain.MainActivity
import com.call.callingthememain.R
import com.call.callingthememain.fragments.HomeFragmentDirections
import com.call.callingthememain.fragments.WallpaperFragmentDirections
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.models.MobileToolsModel
import com.call.callingthememain.utils.AllDataCallTheme
import kotlinx.android.synthetic.main.fragment_mobile_tools.*
import kotlinx.android.synthetic.main.fragment_trending.*
import java.util.ArrayList


class TrendingFragment : Fragment() {

    private var madapter: CallThemeAdapter? = null
    private var mContext: Context? = null
    var myListData: ArrayList<CallThemeModel>? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        Log.d("zzzzzzzzzz TrendingFragment", "onViewCreated:onAttach ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("zzzzzzzzzz TrendingFragment", "onViewCreated:onCreate ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("zzzzzzzzzz TrendingFragment", "onViewCreated:onCreateView ")

        return inflater.inflate(R.layout.fragment_trending, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("zzzzzzzzzz TrendingFragment", "onViewCreated:onViewCreated ")
        setCallThemeAdapter()

    }

    private fun setCallThemeAdapter() {
        myListData = ArrayList()
        myListData= AllDataCallTheme.trendingDataTheme()


        madapter = CallThemeAdapter(
            mContext!!,
            myListData!!,
            object : CallThemeAdapter.OnItemClickListener {
                override fun onItemClick(model: CallThemeModel) {
                    val action = HomeFragmentDirections.actionHomeFragmentToPreviewCallThemeFragment(model)
                    findNavController().navigate(action)

                }
            })
        callThemeRecycler.apply {
            callThemeRecycler.layoutManager = GridLayoutManager(mContext,2)
            callThemeRecycler.adapter = madapter
        }
    }



}