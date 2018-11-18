package studio.sw.mobile.songgoldoctor

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager


class MainActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var tabLayout:TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout?.addTab(tabLayout?.newTab()!!.setText("Map"))
        tabLayout?.addTab(tabLayout?.newTab()!!.setText("Book"))
        tabLayout?.addTab(tabLayout?.newTab()!!.setText("Diagnosis"))
        tabLayout?.addTab(tabLayout?.newTab()!!.setText("Info"))
        tabLayout?.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter:PagerAdapter = PagerAdapter(supportFragmentManager,tabLayout!!.tabCount)
        viewPager?.adapter = pagerAdapter
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout?.addOnTabSelectedListener(TabLayoutOnTabSelectedListener(viewPager))
    }
}
