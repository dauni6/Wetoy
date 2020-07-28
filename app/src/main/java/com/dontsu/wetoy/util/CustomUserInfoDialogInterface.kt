package com.dontsu.wetoy.util

import com.google.android.material.textfield.TextInputEditText


interface CustomUserInfoDialogInterface {

    fun onUserNameCancelClicked(customDialog: CustomUserNameChangeDialog)
    fun onUserNameOkayClicked(customDialog: CustomUserNameChangeDialog, name: String)

    fun onUserPasswordCancelClicked(customDialog: CustomUserPasswordChangeDialog)
    fun onUserPasswordOkayClicked(customDialog: CustomUserPasswordChangeDialog, password: String)

}