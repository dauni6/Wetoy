package com.dontsu.wetoy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dontsu.wetoy.R
import com.dontsu.wetoy.databinding.ActivitySignupBinding
import com.dontsu.wetoy.model.User
import com.dontsu.wetoy.util.DATA_USERS
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    //firebase
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser?.uid
        user?.let {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Data binding
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SignupActivity, R.layout.activity_signup)

        setSupportActionBar(binding.signupActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //뒤로가기 버튼

        setTextChangeListener(binding.userNameET, binding.userNameTIL)
        setTextChangeListener(binding.emailET, binding.emailTIL)
        setTextChangeListener(binding.passwordET, binding.passwordTIL)
        setTextChangeListener(binding.passwordCheckET, binding.passwordCheckTIL)

        signupProgressLayout.setOnTouchListener { v, event ->  true} //동작시 유저가 화면 클릭할 수 없도록 함
    }

    private fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (et == binding.userNameET) {
                        binding.showTextLimit.text = "${it.length}/12"
                    }
                }
                til.isErrorEnabled = false
            }
        })
    }

    fun onSignup(v: View) {
        var proceed = true
        if (binding.userNameET.text.isNullOrEmpty()) {
            binding.userNameTIL.error = "닉네임을 입력해주세요"
            binding.userNameTIL.isErrorEnabled = true
            proceed = false
        }

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

        if (binding.passwordCheckET.text.isNullOrEmpty()) {
            binding.passwordCheckTIL.error = "비밀번호를 재입력해주세요"
            binding.passwordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (binding.passwordET.text.toString() != binding.passwordCheckET.text.toString()) {
            binding.passwordCheckTIL.error = "비밀번호가 맞지 않습니다"
            binding.passwordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) {  // 회원가입
            signupProgressLayout.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(binding.emailET.text.toString(), binding.passwordET.text.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "현재 사용중인 이메일입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        val username = binding.userNameET.text.toString()
                        val email = binding.emailET.text.toString()
                        val user = User(username, email, "")
                        firebaseDB.collection(DATA_USERS).document(firebaseAuth.uid!!).set(user)
                    }
                    signupProgressLayout.visibility = View.GONE
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    signupProgressLayout.visibility = View.GONE
                }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}