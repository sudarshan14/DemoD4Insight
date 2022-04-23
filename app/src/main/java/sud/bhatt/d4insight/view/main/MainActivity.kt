package sud.bhatt.d4insight.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import sud.bhatt.d4insight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /** tabsArray can be received from array resources.
     * but for simplicity using hard coded array*/
    private val tabsArray = arrayOf(
        "Chat",
        "Weather",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, tabsArray.size)
        binding.viewPager.adapter = viewPagerAdapter
//        binding.viewPager.offscreenPageLimit

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()

    }


    override fun onBackPressed() {
        // when not on first tab, switch to first tab when onBackPressed is called
        val currentItem: Int = binding.viewPager.currentItem
        if (currentItem != 0) {
            binding.viewPager.setCurrentItem(currentItem - 1, true)
        } else {
            super.onBackPressed()
        }
    }
}