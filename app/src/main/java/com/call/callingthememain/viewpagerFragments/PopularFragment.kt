package com.call.callingthememain.viewpagerFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.call.callingthememain.Adapters.CallThemeAdapter
import com.call.callingthememain.MainActivity
import com.call.callingthememain.R
import com.call.callingthememain.fragments.HomeFragmentDirections
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.utils.AllDataCallTheme
import kotlinx.android.synthetic.main.fragment_popular.*


class PopularFragment : Fragment() {
    private var madapter: CallThemeAdapter? = null
    private var mContext: Context? = null
    var myListData: ArrayList<CallThemeModel> = ArrayList()


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("zzzzzzzzzz TrendingFragment", "onViewCreated:onViewCreated ")
        setPopularThemeAdapter()

    }

    private fun setPopularThemeAdapter() {
        myListData = AllDataCallTheme.popularDataTheme()

        madapter = CallThemeAdapter(
            mContext!!,
            myListData,
            object : CallThemeAdapter.OnItemClickListener {
                override fun onItemClick(model: CallThemeModel) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToPreviewCallThemeFragment(model)
                    findNavController().navigate(action)

                }

            })
        popularCallThemeRecycler.apply {
            popularCallThemeRecycler.layoutManager = GridLayoutManager(mContext, 2)
            popularCallThemeRecycler.adapter = madapter
        }
    }


}