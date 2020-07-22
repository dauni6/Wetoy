package com.dontsu.wetoy.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dontsu.wetoy.R
import com.dontsu.wetoy.util.CustomDialogInterface
import com.dontsu.wetoy.util.CustomUserNameChangeDialog
import com.dontsu.wetoy.util.CustomUserPasswordChangeDialog
import com.dontsu.wetoy.view.fragments.*
import com.dontsu.wetoy.viewmodel.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), CustomDialogInterface {

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val messageFragment = MessageFragment()
    private val reservationFragment = ReservationFragment()
    private val userFragment = UserFragment()

    lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNV.itemIconTintList = null //null 해줘야 비로소 selector_.xml 이 작동함

        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        viewModel.initializeUser() //유저정보 가져오기

        supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment).commit()

        bottomNV.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.bottom_menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, homeFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    title_text.text = "홈"
                    true
                }
                R.id.bottom_menu_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, searchFragment).commit()
                    search_bar.visibility = View.VISIBLE
                    title_text.text= "검색"
                    true
                }
                R.id.bottom_menu_message -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, messageFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    title_text.text = "메세지"
                    true
                }
                R.id.bottom_menu_reservation -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, reservationFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    title_text.text = "예약"
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction().replace(R.id.home_container, userFragment).commit()
                    search_bar.visibility = View.INVISIBLE
                    title_text.text = "내정보"
                    true
                }
            }
        }
    }

    //닉네임 변경 버튼
    override fun onUserNameOkayClicked(customDialog: CustomUserNameChangeDialog, name: String) {
        Log.d("HomeActivity", "onUserNameOkayClicked() 실행")
        viewModel.userNameChangedComplete(name)
        customDialog.cancel()
    }

    //닉네임 변경 취소 버튼
    override fun onUserNameCancelClicked(customDialog: CustomUserNameChangeDialog) {
        Log.d("HomeActivity", "onUserNameCancelClicked() 실행")
        customDialog.cancel()
    }

    //비밀번호 변경 버튼
    override fun onUserPasswordOkayClicked(customDialog: CustomUserPasswordChangeDialog, password: String) {
        Log.d("HomeActivity", "onUserPasswordOkayClicked() 실행")
        viewModel.userPasswordChangedComplete(password)
        Toast.makeText(this, "비밀번호를 변경했습니다.", Toast.LENGTH_SHORT).show()
        customDialog.cancel()
    }

    //비밀번호 변경 취소 버튼
    override fun onUserPasswordCancelClicked(customDialog: CustomUserPasswordChangeDialog) {
        Log.d("HomeActivity", "onUserPasswordCancelClicked() 실행")
        customDialog.cancel()
    }




}