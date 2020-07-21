package com.dontsu.wetoy.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.dontsu.wetoy.R
import kotlinx.android.synthetic.main.custom_dialog_username_change.*

class CustomUserNameChangeDialog(context: Context, customDialogInterface: CustomDialogInterface): AlertDialog(context) {

    private var customDialogInterface: CustomDialogInterface = customDialogInterface //인터페이스 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_username_change)

        //배경 투명
       /* window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/

        //에디트텍스트
        userNameET.setOnClickListener {
            customDialogInterface.onUserNameChangeClicked(userNameET, userNameET.text.toString())
        }

        //변경버튼
        changeOkayBtn.setOnClickListener {
            customDialogInterface.onOkayClicked(this)
        }

        //취소버튼
        changeCancelBtn.setOnClickListener {
            customDialogInterface.onCancelClicked(this)
        }

    }
}
