package com.call.callingthememain.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.call.callingthememain.Adapters.PixabayAdapter
import com.call.callingthememain.Adapters.SensorAdapter
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.R
import com.call.callingthememain.models.SensorModel
import kotlinx.android.synthetic.main.fragment_battery_information.*
import kotlinx.android.synthetic.main.fragment_sensor_infp.*
import kotlinx.android.synthetic.main.fragment_wallpaper.*


class SensorInfpFragment : Fragment() {
    private lateinit var mContext: Context
    var madapter: SensorAdapter? = null
    var sensorListData: ArrayList<SensorModel>? = null


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
        return inflater.inflate(R.layout.fragment_sensor_infp, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        setSensorAdapter()

    }

    private fun setSensorAdapter() {
        val smm: SensorManager = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        val sensor: List<Sensor>
        sensorListData = java.util.ArrayList()
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        Log.d("TAG", "onItemClick: $sensor")
        val sensorName = java.util.ArrayList<String>()
        sensor.forEach {

            sensorName.add(it.name)
            sensorName.add(it.resolution.toString())
            sensorName.add(it.version.toString())

            sensorListData!!.add(
                SensorModel(
                    it.name,
                    it.resolution.toString(),
                    it.version.toString()
                )
            )

        }
        Log.d("TAG", "onItemClick:sensorName ${sensorListData}")
        Log.d("TAG", "onItemClick:sensorName ${sensorListData!!.size}")
        madapter = SensorAdapter(
            mContext,
            sensorListData!!,
            object : SensorAdapter.OnItemClickListener {
                override fun onItemClick(pos: Int) {


                }

            })
        sensorRecycler.apply {
            sensorRecycler.layoutManager = LinearLayoutManager(mContext)
            sensorRecycler.adapter = madapter
        }

    }
}