package com.call.callingthememain.viewpagerFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.call.callingthememain.Adapters.CallThemeAdapter
import com.call.callingthememain.R
import com.call.callingthememain.models.CallThemeModel
import kotlinx.android.synthetic.main.fragment_recent.*

class RecentFragment : Fragment() {
    private lateinit var mContext: Context
    private var madapter: CallThemeAdapter? = null


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
        return inflater.inflate(R.layout.fragment_recent, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

  /*  private fun recentTheme() {
        madapter = CallThemeAdapter(mContext,
            PreviewCallThemeFragment.recentListData as ArrayList<CallThemeModel> *//* = java.util.ArrayList<com.call.callingthememain.models.CallThemeModel> *//*,
            object : CallThemeAdapter.OnItemClickListener {
                override fun onItemClick(model: CallThemeModel) {

                }

            })
        recentCallThemeRecycler.apply {
            recentCallThemeRecycler.layoutManager=GridLayoutManager(mContext,2)
            recentCallThemeRecycler.adapter=madapter
        }
    }*/

}