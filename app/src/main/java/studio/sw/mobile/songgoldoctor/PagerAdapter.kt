package studio.sw.mobile.songgoldoctor

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

public class PagerAdapter(var fm: FragmentManager?, var numTabs: Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return MapFragment()
            1 -> return BookFragment()
            2 -> return DiagnosisFragment()
            3 -> return InfoFragment()
        }
        return null
    }

    override fun getCount(): Int {
        return numTabs
    }
}