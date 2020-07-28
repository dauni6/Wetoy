package com.dontsu.wetoy.util

import android.app.Dialog

interface CustomWriteDialogInterface {

    fun onAddKeywordOkayClicked(customDialog: CustomWriteAddKeywordDialog, keyword: String)
    fun onAddKeywordCancelClicked(customDialog: CustomWriteAddKeywordDialog)


}