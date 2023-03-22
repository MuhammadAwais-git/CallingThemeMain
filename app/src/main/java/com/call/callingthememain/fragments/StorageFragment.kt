package com.call.callingthememain.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.call.callingthememain.Adapters.CallThemeAdapter
import com.call.callingthememain.Adapters.FavouriteCallThemeAdapter
import com.call.callingthememain.R
import com.call.callingthememain.databinding.FragmentStorageInfoBinding
import com.call.callingthememain.favouriteRoomDatabase.DataViewModel
import com.call.callingthememain.models.CallThemeModel
import com.call.callingthememain.models.FavCallThemeModel
import com.call.callingthememain.viewpagerFragments.PreviewCallThemeFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_recent.*
import kotlinx.android.synthetic.main.fragment_sensor_infp.*
import kotlinx.android.synthetic.main.fragment_sensor_infp.back_arrow
import kotlinx.android.synthetic.main.fragment_storage.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class StorageFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var binding: FragmentStorageInfoBinding
    private lateinit var madapter:FavouriteCallThemeAdapter

    private lateinit var vm: DataViewModel
    companion object{

        var favThemeList = ArrayList<FavCallThemeModel>()
        var listPosition=0
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_storage, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_arrow.setOnClickListener {
          findNavController().popBackStack()
        }

        vm = ViewModelProvider(this)[DataViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            getAllData()
        }
    }

    private fun getAllData() {

        vm.getAllNotes().observe(viewLifecycleOwner, Observer {
            favThemeList = it as ArrayList<FavCallThemeModel>
            if(favThemeList.isEmpty()){
                Log.d("TAG", "getAllData:empty list")
            }else {
                setUpRecyclerView(favThemeList)
                Log.d("TAG", "getAllData:favlist ${favThemeList.size}")

            }
        })
    }
    private fun setUpRecyclerView(mList:ArrayList<FavCallThemeModel>) {
        madapter = FavouriteCallThemeAdapter(
            listPosition,mContext,mList,
            object : FavouriteCallThemeAdapter.OnItemClickListener {
                override fun onItemClick(model: FavCallThemeModel) {

                    val action = StorageFragmentDirections.actionStorageFragmentToPreviewFavouriteFragment(model)
                    findNavController().navigate(action)

                }

            })
        favouriteRecycler.apply {
            favouriteRecycler.layoutManager= GridLayoutManager(mContext,2)
            favouriteRecycler.adapter=madapter
        }
    }
}