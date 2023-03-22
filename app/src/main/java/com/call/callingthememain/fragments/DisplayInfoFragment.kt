package com.call.callingthememain.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.call.callingthememain.R
import kotlinx.android.synthetic.main.fragment_display_info.*


class DisplayInfoFragment : Fragment() {
    private lateinit var mContext: Context

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
        return inflater.inflate(R.layout.fragment_display_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        getScreenResolution(mContext)

    }

    private fun getScreenResolution(context: Context): String? {
        val screenSize = resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK

        val ScreenSize: String
        ScreenSize = when (screenSize) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large screen"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal screen"
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small screen"
            else -> "Screen size is neither large, normal or small"
        }

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        val refreshRating = display.refreshRate
        val hrtz=String.format("%.0f", refreshRating)


        settxtScreenSize.text=ScreenSize
        settxtRefreshRate.text=hrtz+" Hz"
        settxtWidth.text=width.toString()+" pixels"
        settxtHeight.text=height.toString()+" pixels"
        settxtDensity.text=metrics.density.toString()
        settxtDensitydpi.text= metrics.densityDpi.toString()
        settxtXdpi.text= metrics.xdpi.toString()
        settxtYdpi.text= metrics.ydpi.toString()



        return "{$width,$height}"

    }


}