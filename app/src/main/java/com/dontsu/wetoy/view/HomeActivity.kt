package com.dontsu.wetoy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dontsu.wetoy.R
import com.dontsu.wetoy.viewmodel.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val messageFragment = MessageFragment()
    private val reservationFragment = ReservationFragment()
    private val userFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNV.itemIconTintList = null //null 해줘야 비로소 selector_.xml 이 작동함

        supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment).commit()


        bottomNV.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.bottom_menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment).commit()
                    title_text.text = "홈"
                    search_bar.visibility = View.INVISIBLE
                    true
                }
                R.id.bottom_menu_search -> {
                    title_text.text= "검색"
                    search_bar.visibility = View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, searchFragment).commit()
                    true
                }
                R.id.bottom_menu_message -> {
                    title_text.text = "메세지"
                    search_bar.visibility = View.INVISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, messageFragment).commit()
                    true
                }
                R.id.bottom_menu_reservation -> {
                    title_text.text = "예약"
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, reservationFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    true
                }
                else -> {
                    title_text.text = "내정보"
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, userFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    true
                }
            }
        }
    }




}