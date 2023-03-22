package com.call.callingthememain.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.fragment_system__info_.*

class SystemInfoFragment : Fragment() {
    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system__info_, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        setData()

    }

    @SuppressLint("HardwareIds")
    private fun setData() {
        settxtBrand.text = Build.BRAND
        settxtDeviceId.text = Settings.Secure.getString(
            mContext!!.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        settxtModel.text = Build.MODEL
        settxtManufacture.text = Build.MANUFACTURER
        settxtManufacture.isSelected = true
        settxtVersion.text = Build.VERSION.RELEASE
        settxtFingerprint.text = Build.FINGERPRINT
        settxtFingerprint.isSelected = true

    }

    @SuppressLint("HardwareIds")
    private fun getSystemDetail(): String {
        return "Brand: ${Build.BRAND} \n" +
                "DeviceID: ${
                    Settings.Secure.getString(
                        mContext!!.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                } \n" +
                "Model: ${Build.MODEL} \n" +
                "ID: ${Build.ID} \n" +
                "SDK: ${Build.VERSION.SDK_INT} \n" +
                "Manufacture: ${Build.MANUFACTURER} \n" +
                "Brand: ${Build.BRAND} \n" +
                "User: ${Build.USER} \n" +
                "Type: ${Build.TYPE} \n" +
                "Base: ${Build.VERSION_CODES.BASE} \n" +
                "Incremental: ${Build.VERSION.INCREMENTAL} \n" +
                "Board: ${Build.BOARD} \n" +
                "Host: ${Build.HOST} \n" +
                "FingerPrint: ${Build.FINGERPRINT} \n" +
                "Version Code: ${Build.VERSION.RELEASE}"
    }


}