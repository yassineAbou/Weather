package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.weather.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

          setupToolbar()
         setupViewpager2()

    }

    private fun setupViewpager2() {
        val pagerAdapter = FragmentSlidePagerAdapter(this)
        binding.apply {
            viewpager2.adapter = pagerAdapter
            wormDotsIndicator.setViewPager2(binding.viewpager2)

            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> locationBar()
                        1 -> todayBar()
                        2 -> next24HoursBar()
                        3 -> next5DaysBar()
                        else -> return
                    }
                }
            })
        }
    }

    private fun next5DaysBar() {
        binding.apply {
            toolbar.title = "Location"
            toolbar.subtitle = "Next 5 days"
        }
    }

    private fun next24HoursBar() {
        binding.apply {
            toolbar.title = "Location"
            toolbar.subtitle = "Next 24 hours"
        }
    }

    private fun todayBar() {
        binding.apply {
            toolbar.title = "Location"
            toolbar.subtitle = "Day, time"
        }
    }

    private fun locationBar() {
        binding.apply {
            toolbar.title = "Location"
            toolbar.subtitle = ""
        }

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }


}