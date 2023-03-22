package com.call.callingthememain.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.fragment_battery_information.*
import kotlinx.android.synthetic.main.fragment_battery_information.txtProgressbar
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.util.*


class BatteryInformationFragment : Fragment() {
    private lateinit var mContext: Context
    var intentfilter: IntentFilter? = null
    var deviceHealth = 0

    private var txtProgressbar:TextView?=null
    private var mProgressbar: ProgressBar?=null
    var batteryVol = 0
    var fullVoltage = 0f
    private var batteryPct = 0F

    var i = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =  inflater.inflate(R.layout.fragment_battery_information, container, false)
        txtProgressbar = view.findViewById(R.id.txtProgressbar)
        mProgressbar = view.findViewById(R.id.progressbarBattery)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_arrowb.setOnClickListener {
            findNavController().popBackStack()

        }

        intentfilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
//        mContext.registerReceiver(broadcastreceiver, intentfilter)


        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {

                    if (i <= batteryPct) {
                        Log.d("TAG", "run: $i   $batteryPct")

                        txtProgressbar!!.text = "" + i + "%"
                        mProgressbar!!.progress = i
                        i++
                        handler.postDelayed(this, 30)
                    } else {
                        handler.removeCallbacks(this)
                    }

            }
        }, 100)

    }

    private val broadcastreceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            batteryVol = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
            fullVoltage = (batteryVol * 0.001).toFloat()
            setTxtVoltage.text = fullVoltage.toString() + "Volt"

            deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                setTxtHealth.text = "Cold"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                setTxtHealth.text = "Dead"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                setTxtHealth.text = "Good"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                setTxtHealth.text = "OverHeat"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                setTxtHealth.text = "Over voltage"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                setTxtHealth.text = "Unknown"
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                setTxtHealth.text = "Unspecified Failure"
            }

            var bstatus: String? = "isChrg=false usbChrg=false acChrg=false wlChrg=false 0% t=70°F"
            val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, iFilter)
            val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL
            val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            batteryPct = level * 100 / scale.toFloat()
            //How are we charging?
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
            val wlCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS
            val temp = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
            val tempTwo = temp.toFloat() / 10
            bstatus = java.lang.String.format(
                Locale.US, "isChrg=%b usbChrg=%b acChrg=%b wlChrg=%b %.0f%% t=%.2f°F",
                isCharging,
                usbCharge,
                acCharge,
                wlCharge,
                batteryPct,
                tempTwo
            )
            val tempCelsius = ((tempTwo - 32) * 5) / 9
            setBatteryTemperature.text = tempTwo.toString() + "°C"
            if (isCharging) {
                imgBatteryshow.visibility = View.VISIBLE
            } else {
                imgBatteryshow.visibility = View.INVISIBLE
            }
            if (wlCharge) {
                imgWirelessshow.visibility = View.VISIBLE
            } else {
                imgWirelessshow.visibility = View.INVISIBLE
            }
            if (usbCharge) {
                imgUSBshow.visibility = View.VISIBLE
            } else {
                imgUSBshow.visibility = View.INVISIBLE
            }
            if (acCharge) {
                imgAcshow.visibility = View.VISIBLE
            } else {
                imgAcshow.visibility = View.INVISIBLE
            }

        }
    }

    override fun onPause() {
        mContext.unregisterReceiver(broadcastreceiver)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mContext.registerReceiver(broadcastreceiver, intentfilter)
    }



}