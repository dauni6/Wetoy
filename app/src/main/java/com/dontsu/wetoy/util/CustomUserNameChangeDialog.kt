package com.dontsu.wetoy.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dontsu.wetoy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_dialog_username_change.*

class CustomUserNameChangeDialog(context: Context, customUserInfoDialogInterface: CustomUserInfoDialogInterface): Dialog(context) {

    private var customUserInfoDialogInterface: CustomUserInfoDialogInterface = customUserInfoDialogInterface //인터페이스 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_username_change)

        //배경 투명
        /*window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) 투명 하니까 완전 안보임 걍 */

        setTextChangeListener(userNameET, userNameTIL)

        //변경버튼
        changeOkayBtn.setOnClickListener {
            continueChangeUserName()
        }

        //취소버튼
        changeCancelBtn.setOnClickListener {
            customUserInfoDialogInterface.onUserNameCancelClicked(this)
        }



    }

    private fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    this@CustomUserNameChangeDialog.showTextLimit.text = "${it.length}/12"
                }
                til.isErrorEnabled = false
            }
        })
    }

    private fun continueChangeUserName() {
        var proceed = true
        if(userNameET.text.isNullOrEmpty()) {
            userNameTIL.error = "닉네임을 적어주세요!"
            userNameTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) {
            customUserInfoDialogInterface.onUserNameOkayClicked(this, userNameET.text.toString())
        }
    }
}
