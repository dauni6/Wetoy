package com.dontsu.wetoy.util

import android.app.Dialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.dontsu.wetoy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_dialog_username_change.*

class CustomUserNameChangeDialog(context: Context, customDialogInterface: CustomDialogInterface): Dialog(context) {

    private var customDialogInterface: CustomDialogInterface = customDialogInterface //인터페이스 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_username_change)

        //배경 투명
        /*window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) 투명 하니까 완전 안보임 걍 */




        //변경버튼
        changeOkayBtn.setOnClickListener {
            customDialogInterface.onOkayClicked(this, userNameET.text.toString())
        }

        //취소버튼
        changeCancelBtn.setOnClickListener {
            customDialogInterface.onCancelClicked(this)
        }

    }

    private fun setTextChangeListener(et: TextInputEditText, til: TextInputLayout) {
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}
