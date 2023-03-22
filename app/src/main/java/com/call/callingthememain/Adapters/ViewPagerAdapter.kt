package com.call.callingthememain.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.call.callingthememain.viewpagerFragments.MotionFragment
import com.call.callingthememain.viewpagerFragments.PopularFragment
import com.call.callingthememain.viewpagerFragments.RecentFragment
import com.call.callingthememain.viewpagerFragments.TrendingFragment

class ViewPagerAdapter (private val mContext: Context,fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
               TrendingFragment()
            }
            1 -> {
                MotionFragment()
            }
            2 -> {
               PopularFragment()
            }
//            3 -> {
//                RecentFragment()
//            }
            else -> {
                TrendingFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Theme"
            }
            1 -> {
                return "Edge Lighting"
            }
            2 -> {
                return "Popular"
            }
//            3 -> {
//                return "Recent"
//            }
        }
        return super.getPageTitle(position)
    }

}