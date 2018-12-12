package studio.sw.mobile.songgoldoctor

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton

class FavoriteListActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var backButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        tabLayout = findViewById(R.id.favoriteTabLayout)
        viewPager = findViewById(R.id.favoriteViewPager)
        backButton = findViewById(R.id.book_activity_back_button)

        backButton.setOnClickListener {
            finish()
        }

        tabLayout.addTab(tabLayout.newTab().setText("즐겨찾기"))
        tabLayout.addTab(tabLayout.newTab().setText("최근목록"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val pagerAdapter = PagerAdapter(supportFragmentManager, arrayListOf(FavoriteFragment(), RecentFragment()))
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayoutOnTabSelectedListener(viewPager))
    }


}