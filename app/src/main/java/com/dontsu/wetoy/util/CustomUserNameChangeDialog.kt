package com.dontsu.wetoy.util

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.dontsu.wetoy.R
import kotlinx.android.synthetic.main.custom_dialog_username_change.*

class CustomUserNameChangeDialog(context: Context, customDialogInterface: CustomDialogInterface): AlertDialog(context) {

    private var customDialogInterface: CustomDialogInterface = customDialogInterface //인터페이스 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_username_change)

        //배경 투명
       /* window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) 투명 하니까 완전 안보임 걍 */

        //변경버튼
        changeOkayBtn.setOnClickListener {
            customDialogInterface.onOkayClicked(this, userNameET.text.toString())
        }

        //취소버튼
        changeCancelBtn.setOnClickListener {
            customDialogInterface.onCancelClicked(this)
        }

    }
}
