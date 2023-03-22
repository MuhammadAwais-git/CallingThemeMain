package com.call.callingthememain.fragments

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StatFs
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.call.callingthememain.R
import com.call.callingthememain.utils.Helper
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.android.synthetic.main.fragment_storage_info.*
import java.io.File
import java.text.DecimalFormat


class StorageInfoFragment : Fragment() {
    private var mContext: Context? = null
    private var txtProgressbar:TextView?=null
    private var progressbarStorage: ProgressBar?=null
    var i=0

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
        val view:View= inflater.inflate(R.layout.fragment_storage_info, container, false)
      txtProgressbar=view.findViewById<TextView>(R.id.txtProgressbarS)
      progressbarStorage=view.findViewById<ProgressBar>(R.id.progressbarStorage)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_arrowS.setOnClickListener {
            findNavController().popBackStack()
        }

        val mTotal = Helper.getTotalDirectorySize(2)
        val mFree = Helper.bytesToHuman(Helper.getTotalUSedStorageSize(3), 2)
        txtFree.text = Helper.bytesToHuman(Helper.getTotalUSedStorageSize(3), 1)
        txtTotal.text = Helper.getTotalDirectorySize(1)
        val usedStorage="${DecimalFormat("##.##").format(mTotal.toFloat()-mFree.toFloat())} GB"
        val usedStorage1=DecimalFormat("##").format(mTotal.toFloat()-mFree.toFloat())
         txtUsed.text = usedStorage

        val storagePercentage = ((usedStorage1.toFloat() * 100) / mTotal.toFloat())

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {

                if (i <= storagePercentage) {
                    txtProgressbar?.text = "${i}%"
                    progressbarStorage?.progress = i

                    i++
                    handler.postDelayed(this, 30)
                } else {
                    handler.removeCallbacks(this)
                }
            }
        }, 100)

    }
}