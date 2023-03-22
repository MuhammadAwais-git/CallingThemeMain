package com.call.callingthememain.fragments

import android.app.ActivityManager
import android.content.Context
import android.icu.text.DecimalFormat
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.call.callingthememain.R
import com.call.callingthememain.databinding.FragmentDashBoardBinding
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.io.BufferedReader
import java.io.InputStreamReader


class DashBoardFragment : Fragment() {
    private lateinit var binding: FragmentDashBoardBinding
    private var mContext: Context? = null


    val wifiList = listOf(43, 68, 87, 97, 92, 95, 89, 63, 88, 82, 90, 99)

    var i = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_arrow.setOnClickListener {
            findNavController().popBackStack(R.id.dashBoardFragment,true)
        }

        setTxtTemperature.text = cpuTemperature().toString()
        tempProgressbar.progress = cpuTemperature().toInt()

        val handlerTemp = Handler()
        handlerTemp.postDelayed(object : Runnable {
            override fun run() {
                if (i <= cpuTemperature().toInt()) {
                    binding.tempProgressbar.progress = i
                    i++
                    handlerTemp.postDelayed(this, 30)
                } else {
                    handlerTemp.removeCallbacks(this)
                }
            }
        }, 100)

        val maxValueWifi = wifiList.random()


        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {

                if (i <= maxValueWifi) {
                    binding.txtProgressbarD.text = "" + i
                    binding.circularProgressbarD.progress = i

                    i++
                    handler.postDelayed(this, 30)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        }, 100)


        setRamData()
//        wifiSpeed()
        getNetworkSpeed(mContext!!)

    }

    private fun setRamData() {
        val actManager = mContext?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val availMemoryT = memInfo.availMem.toDouble() / (1024 * 1024 * 1024)
        val availMemory = memInfo.availMem
        val totalMemory = memInfo.totalMem
        val usedMemory = totalMemory - availMemory
        val ramPercentage = ((usedMemory * 100) / totalMemory)

        settxtTotalRam.text = bytesToHumanHome(totalMemory) + "GB"
        settxtfreeRam.text = bytesToHuman(availMemory, 1)
        settxtusedRam.text = bytesToHuman(usedMemory, 1)


        val handlerrRam = Handler()
        handlerrRam.postDelayed(object : Runnable {
            override fun run() {
                if (i <= ramPercentage) {
                    binding.txtProgressbarRam.text = "" + i + "%"
                    binding.circularProgressbarRam.progress = i
                    i++
                    handlerrRam.postDelayed(this, 30)
                } else {
                    handlerrRam.removeCallbacks(this)
                }
            }
        }, 100)

    }

    private fun bytesToHumanHome(size: Long): String {
        var totalSize = "0"
        var sizeUnit = "KB"
        val mKb: Long = 1024
        val mMb = mKb * 1024
        val mGb = mMb * 1024
        val mTb = mGb * 1024
        val mPb = mTb * 1024
        val mEb = mPb * 1024
        if (size in mKb..mMb) {
            totalSize = DecimalFormat("#").format(size.toDouble() / mKb)

        }
        if (size in mMb until mGb) {
            totalSize = DecimalFormat("#").format(size.toDouble() / mMb).toString()
        }
        if (size in mGb until mTb) {
            totalSize = DecimalFormat("#").format(size.toDouble() / mGb).toString()
        }
        if (size in mTb until mPb) {
            totalSize = DecimalFormat("#").format(size.toDouble() / mTb).toString()
        }
        if (size in mPb until mEb) {
            totalSize = DecimalFormat("#").format(size.toDouble() / mPb).toString()
        }
        if (size >= mEb) {
            DecimalFormat("#").format(size.toDouble() / mEb).toString()
        }
        return totalSize
    }

    fun bytesToHuman(size: Long, withUnit: Int): String {
        var totalSize = "0"
        var sizeUnit = "KB"
        val mKb: Long = 1024
        val mMb = mKb * 1024
        val mGb = mMb * 1024
        val mTb = mGb * 1024
        val mPb = mTb * 1024
        val mEb = mPb * 1024
        if (size in mKb..mMb) {
            totalSize = DecimalFormat("#.##").format(size.toDouble() / mKb)
            sizeUnit = "KB"
            // totalSize= floatForm(size.toDouble() / mKb).toString() + " KB"
        }
        if (size in mMb until mGb) {
            totalSize = DecimalFormat("#.##").format(size.toDouble() / mMb).toString()
            sizeUnit = "MB"
        }
        if (size in mGb until mTb) {
            totalSize = DecimalFormat("#.##").format(size.toDouble() / mGb).toString()
            sizeUnit = "GB"
        }
        if (size in mTb until mPb) {
            totalSize = DecimalFormat("#.##").format(size.toDouble() / mTb).toString()
            sizeUnit = "TB"
        }
        if (size in mPb until mEb) {
            totalSize = DecimalFormat("#.##").format(size.toDouble() / mPb).toString()
            sizeUnit = "PB"
        }
        if (size >= mEb) {
            DecimalFormat("#.##").format(size.toDouble() / mEb).toString()
            sizeUnit = "EB"
        }

        return if (withUnit == 1) {
            "$totalSize $sizeUnit"
        } else {
            "$totalSize "
        }
    }

    private fun cpuTemperature(): Float {
        val process: Process
        return try {
            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp")
            process.waitFor()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val line: String = reader.readLine()
            if (line.isNotEmpty()) {
                val temp = line.toFloat()
                temp / 1000.0f
            } else {
                51.0f
            }
        } catch (e: Exception) {
            e.printStackTrace()
            30.0f
        }
    }

    private fun wifiSpeed() {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo

        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        val downSpeed = nc!!.linkDownstreamBandwidthKbps
        val upSpeed = nc.linkUpstreamBandwidthKbps
        val speeedInMp = upSpeed / 1024
        settxtWifiSpeed.text = speeedInMp.toString()
        Log.d("TAG", "wifiSpeed:downSpeed $downSpeed  upSpeed $upSpeed  netInfo ${netInfo!!.type}")
    }

    fun getNetworkSpeed(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nc = cm.getNetworkCapabilities(cm.activeNetwork)
            val downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000)
            Log.d("TAG", "getNetworkSpeed: ssss$downSpeed ")
            settxtWifiSpeed.text = downSpeed.toString() + "Kbps"
            "${downSpeed ?: 0} Kbps"
        } else {
            "-"
        }
    }


}