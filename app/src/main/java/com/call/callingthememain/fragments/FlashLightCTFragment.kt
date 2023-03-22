package com.call.callingthememain.fragments


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER
import android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.call.callingthememain.databinding.FragmentFlashLightCTBinding
import com.call.callingthememain.utils.Time
import kotlinx.android.synthetic.main.fragment_flash_alert.*


class FlashLightCTFragment : Fragment() {
    private lateinit var binding: FragmentFlashLightCTBinding
    private lateinit var mContext: Context

    private val TAG = "flashLog:"
    private var mInterval: Long = 0
    private var mHandler: Handler? = null
    var freq = 0
    private var isFlashOn = false
    private var stopFlicker = false
    var time: Time? = null
    private var flashCondition = false
     var mCameraManager: CameraManager? = null
    private var cameraId: String? = null
    var progressValue = 0

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
        binding = FragmentFlashLightCTBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flashSeekBar.visibility = View.GONE
        binding.seekbarBackground.visibility = View.GONE
        click()


    }

    private fun click() {

        time = Time()
        mHandler = Handler()

        binding.viewFlash.setOnClickListener {
            if (isFlashOn) {
                binding.seekbarBackground.visibility = View.GONE
                binding.flashSeekBar.visibility = View.GONE
                binding.flashSeekBar.progress = 0
                isFlashOn = false
                imgFlashOff.visibility = View.VISIBLE
                imgFlashOn.visibility = View.INVISIBLE
                stopRepeatingTask()
                setFlashOn(false)

            } else {
                stopFlicker = false
                isFlashOn = true
                imgFlashOff.visibility = View.INVISIBLE
                imgFlashOn.visibility = View.VISIBLE
                checkForResources()
                setFlashOn(true)
                binding.flashSeekBar.visibility = View.VISIBLE
                binding.seekbarBackground.visibility = View.VISIBLE
                seekBarMethod()

            }
        }
    }

    fun startRepeatingTask() {
        mStatusChecker.run()
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            Log.d(TAG, "run: ")
            try {
                if (flashCondition) {
                    flashCondition = false
                    setFlashOn(false)
//                    binding.imgFlashOn.visibility = View.GONE

                } else {
                    flashCondition = true
                    setFlashOn(true)
                    binding.imgFlashOn.visibility = View.VISIBLE
                }
            } finally {

                mHandler?.postDelayed(this, mInterval)
            }
        }
    }

    private fun checkForResources() {
        val pm = mContext.packageManager
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(mContext, "NO_CAMERA_ERROR", Toast.LENGTH_SHORT).show()
            return
        } else {
            initialiseResourcesCamera2()
        }
    }

    private fun initialiseResourcesCamera2() {
        mCameraManager = mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = mCameraManager?.cameraIdList?.get(0)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


        fun setFlashOn(enable: Boolean) {
            Log.d("FlashLg:", "setFlashOn: $enable")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mCameraManager!!.setTorchMode(cameraId!!, enable)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

        }


    private fun seekBarMethod() {
        /*SeekBar which will indicate value of brightness of torch*/
        binding.flashSeekBar.max = 10
        binding.flashSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "onProgressChanged: $progress")
                progressValue = progress
                freq = progressValue
                time!!.setSleepTime(freq)
                mInterval = time!!.sleepTime
                if (progressValue == 0) {
                    stopRepeatingTask()
                    // setFlashOn(true);
                } else {
                    startRepeatingTask()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // flashFlicker();
            }
        })
    }

    private fun releaseCamera() {
        setFlashOn(false)
        binding.flashSeekBar.progress = 0
        isFlashOn = false
    }

    override fun onDestroyView() {
        if (isFlashOn) {
            releaseCamera()
            stopRepeatingTask()
        }
        super.onDestroyView()
    }

    fun stopRepeatingTask() {
        mHandler?.removeCallbacks(mStatusChecker)
        Log.d(TAG, "stopRepeatingTask: ")
    }


}