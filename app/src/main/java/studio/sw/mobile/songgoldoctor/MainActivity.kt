package studio.sw.mobile.songgoldoctor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager

class MainActivity : AppCompatActivity() {
    val permissionManager = PermissionManager(this)
    lateinit var tabLayout:TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Permission setup
        permissionManager.setup()
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Map"))
        tabLayout.addTab(tabLayout.newTab().setText("Book"))
        tabLayout.addTab(tabLayout.newTab().setText("Diagnosis"))
        tabLayout.addTab(tabLayout.newTab().setText("Info"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = PagerAdapter(supportFragmentManager,
            arrayListOf(MapFragment(),
                        DiagnosisFragment(),
                        BookFragment(),
                        InfoFragment()))
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayoutOnTabSelectedListener(viewPager))
    }


}
