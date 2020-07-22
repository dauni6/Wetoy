package com.dontsu.wetoy.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dontsu.wetoy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_dialog_userpassword_change.*

class CustomUserPasswordChangeDialog(context: Context, customDialogInterface: CustomDialogInterface): Dialog(context) {

    private var customDialogInterface: CustomDialogInterface = customDialogInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_userpassword_change)

        setTextChangeListener(userPasswordET, userPasswordTIL)
        setTextChangeListener(userPasswordCheckET, userPasswordCheckTIL)

        changeOkayBtn.setOnClickListener {
            continueChangeUserPassword()
        }

        changeCancelBtn.setOnClickListener {
            customDialogInterface.onUserPasswordCancelClicked(this)
        }
    }


    private fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               til.isErrorEnabled = false
            }
        })
    }

    private fun continueChangeUserPassword() {
        var proceed = true
        if (userPasswordET.text.isNullOrEmpty()) {
            userPasswordTIL.error = "비밀번호를 입력해주세요!"
            userPasswordTIL.isErrorEnabled = true
            proceed = false
        }
        if (userPasswordCheckET.text.isNullOrEmpty()) {
            userPasswordCheckTIL.error = "비밀번호를 재입력해주세요!"
            userPasswordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (userPasswordET.text.toString() != userPasswordCheckET.text.toString()) {
            userPasswordCheckTIL.error = "비밀번호가 맞지 않습니다!"
            userPasswordCheckTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) {
            customDialogInterface.onUserPasswordOkayClicked(this, userPasswordCheckET.text.toString())
        }


    }


}