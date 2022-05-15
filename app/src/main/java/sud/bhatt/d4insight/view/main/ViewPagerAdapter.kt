package sud.bhatt.d4insight.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import sud.bhatt.d4insight.utils.UNI_TAG
import sud.bhatt.d4insight.utils.debugLogger
import sud.bhatt.d4insight.view.chat.ChatFragment
import sud.bhatt.d4insight.view.weather.WeatherFragment


private const val NUM_TABS = 2

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle,
    private val size: Int
) :
    FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        debugLogger(UNI_TAG, "createFragment viewpager")
        return when (position) {
            0 -> ChatFragment()
            else -> WeatherFragment()
        }

    }

}