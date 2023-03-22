package com.call.callingthememain.fragments

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.fragment_flash_alert.*


class FlashAlertFragment : Fragment() {
    private var mContext: Context? = null
    var isLightOn = false
    var mBlinkThread: Thread?=null
    //flash light
    var mCameraManager: CameraManager? = null
    var mCameraId: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flash_alert, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //flash light
//        mCameraManager = mContext!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        mCameraManager = mContext!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            mCameraId = mCameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }


        viewFlash.setOnClickListener {
            if (isLightOn) {
                isLightOn = false
                setFlashBlink(isLightOn)
                Log.d("TAG", "onViewCreated:11 $isLightOn")
                imgFlashOff.visibility = View.VISIBLE
                imgFlashOn.visibility = View.INVISIBLE
                switchFlashLight(false)


            } else {
                isLightOn = true
                Log.d("TAG", "onViewCreated:1 $isLightOn")
                setFlashBlink(isLightOn)
                switchFlashLight(true)

                imgFlashOff.visibility = View.INVISIBLE
                imgFlashOn.visibility = View.VISIBLE

            }

        }
    }

    private fun setFlashBlink(b: Boolean) {
        if (b) {
            Log.d("TAG", "setFlashBlink:22 $b ")
            flashSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                    Log.d("TAG", "onProgressChanged: progress $progress")
                    flashBlink(progress + 200)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
//        }else{
//            Log.d("TAG", "setFlashBlink: 33 $b ")
//            mBlinkThread?.let {
//                if(it.isAlive){
//                    it.stop()
//                }
//            }
//        }
    }

    fun switchFlashLight(status: Boolean) {
        try {
            mCameraManager!!.setTorchMode(mCameraId!!, status)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @Throws(InterruptedException::class)
    fun flashBlink(progress: Int) {
        mBlinkThread = object : Thread() {
            val blinkDelay: Long = 50

            override fun run() {
                for (i in 0..49) {
                    switchFlashLight(true)
                    try {
                        sleep(progress.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    switchFlashLight(false)
                    try {
                        sleep(progress.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        (mBlinkThread as Thread).start()

    }

}