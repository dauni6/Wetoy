package com.dontsu.wetoy.util

import com.google.android.material.textfield.TextInputEditText


interface CustomDialogInterface {

    fun onCancelClicked(customDialog: CustomUserNameChangeDialog)
    fun onOkayClicked(customDialog: CustomUserNameChangeDialog, name: String)

}