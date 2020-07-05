package com.dontsu.wetoy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dontsu.wetoy.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    
    //이메일로 시작하기(회원가입 액티비티로)
    fun onSignup(v: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
    
    //카카오 계정으로 시작하기
    
    //네이버 계정으로 시작하기
    
    //구글 계정으로 시작하기

    
}