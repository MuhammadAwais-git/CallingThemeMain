package com.call.callingthememain.fragments


import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorManager
import android.icu.text.DecimalFormat
import android.net.ConnectivityManager
import android.os.*
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.callingthememain.Adapters.MobileToolsAdapter
import com.call.callingthememain.R
import com.call.callingthememain.models.MobileToolsModel
import com.call.callingthememain.models.SensorModel
import kotlinx.android.synthetic.main.fragment_mobile_tools.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Process
import java.util.*


class MobileToolsFragment : Fragment() {
    private var mContext: Context? = null
    var myListData: ArrayList<MobileToolsModel>? = null
    var madapter: MobileToolsAdapter? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mobile_tools, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMobileToolsAdapter()

    }

    private fun setMobileToolsAdapter() {
        myListData = ArrayList()
        myListData!!.add(MobileToolsModel("System \nInformation", R.drawable.system_info,R.color.blue_moblietools))
        myListData!!.add(MobileToolsModel("Dashboard", R.drawable.dash_board,R.color.purple_mobiletools))
        myListData!!.add(MobileToolsModel("Battery \nInformation", R.drawable.battery_info,R.color.green_mobiletools))
        myListData!!.add(MobileToolsModel(" Sensor \nInformation", R.drawable.sensor_in,R.color.orange_mobiletools))
        myListData!!.add(MobileToolsModel("Storage \nInformation", R.drawable.system_info,R.color.darkgreen_mobiletools))
        myListData!!.add(MobileToolsModel("Display \nInformation", R.drawable.display_info,R.color.redlight_mobiletools))

        madapter = MobileToolsAdapter(
            mContext!!,
            myListData!!,
            object : MobileToolsAdapter.OnItemClickListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> {
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_system_Info_Fragment)
                        }
                        1 -> {
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_dashBoardFragment)
                        }
                        2 -> {
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_batteryInformationFragment)

                        }
                        3 -> {
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_sensorInfpFragment)

                        }
                        4 ->{
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_storageInfoFragment)
                        }
                        5->{
                            findNavController().navigate(R.id.action_mobileToolsFragment_to_displayInfoFragment)
                        }
                    }
                }
            })
        mobileToolsRecycler.apply {
            mobileToolsRecycler.layoutManager = GridLayoutManager(mContext,2)
            mobileToolsRecycler.adapter = madapter
        }
    }
}