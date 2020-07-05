package com.dontsu.wetoy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.dontsu.wetoy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setTextChangeListener(userNameET, userNameTIL)
        setTextChangeListener(emailET, emailTIL)
        setTextChangeListener(passwordET, passwordTIL)
        setTextChangeListener(passwordCheckET, passwordCheckTIL)
    }

    fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (et == userNameET) {
                        showTextLimit.text = "${it.length}/12"
                    }
                }
                til.isErrorEnabled = false
            }
        })
    }

    fun onSignup(v: View) {
        var proceed = true
        if (userNameET.text.isNullOrEmpty()) {
            userNameTIL.error = "닉네임을 입력해주세요"
            userNameTIL.isErrorEnabled = true
            proceed = false
        }

        if (emailET.text.isNullOrEmpty()) {
            emailTIL.error = "이메일을 입력해주세요"
            emailTIL.isErrorEnabled = true
            proceed = false
        }

        if (passwordET.text.isNullOrEmpty()) {
            passwordTIL.error = "비밀번호를 입력해주세요"
            passwordTIL.isErrorEnabled = true
            proceed = false
        }

        if (passwordCheckET.text.isNullOrEmpty()) {
            passwordCheckTIL.error = "비밀번호를 재입력해주세요"
            passwordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (passwordET.text.toString() != passwordCheckET.text.toString()) {
            passwordCheckTIL.error = "비밀번호가 맞지 않습니다"
            passwordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) {
            Toast.makeText(this, "모두 확인됨", Toast.LENGTH_SHORT).show()
        }
    }


}