package com.dontsu.wetoy.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dontsu.wetoy.R
import com.dontsu.wetoy.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser?.uid
        user?.let {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //data binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)

        setTextChangeListener(binding.emailET, binding.emailTIL)
        setTextChangeListener(binding.passwordET, binding.passwordTIL)

        loginProgressLayout.setOnTouchListener { v, event ->  true} //동작시 유저가 화면 클릭할 수 없도록 함

    }

    private fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               til.isErrorEnabled = false
            }
        })
    }

    //로그인버튼
    fun onLogin(v: View) {
        var proceed = true
        if (binding.emailET.text.isNullOrEmpty()) {
            binding.emailTIL.error = "이메일을 입력해주세요"
            binding.emailTIL.isErrorEnabled = true
            proceed = false
        }

        if (binding.passwordET.text.isNullOrEmpty()) {
            binding.passwordTIL.error = "비밀번호를 입력해주세요"
            binding.passwordTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) { //로그인
            loginProgressLayout.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(binding.emailET.text.toString(), binding.passwordET.text.toString()) //로그인시도
                .addOnCompleteListener {
                    if (!it.isSuccessful){
                        loginProgressLayout.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    loginProgressLayout.visibility = View.GONE
                }
        }
    }

    //이메일로 시작하기(회원가입 액티비티로)
    fun onSignup(v: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    //카카오 계정으로 시작하기

    //네이버 계정으로 시작하기

    //구글 계정으로 시작하기

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }


}