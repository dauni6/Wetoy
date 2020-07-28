package com.dontsu.wetoy.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.dontsu.wetoy.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.custom_dialog_write_add_keyword.*

class CustomWriteAddKeywordDialog(context: Context, customWriteDialogInterface: CustomWriteDialogInterface): Dialog(context) {

    private var customWriteDialogInterface: CustomWriteDialogInterface = customWriteDialogInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog_write_add_keyword)

        setTextChangeListener(keywordET, keywordTIL)

        addOkayBtn.setOnClickListener {
            continueAddKeyword()
        }

        addCancelBtn.setOnClickListener {
            customWriteDialogInterface.onAddKeywordCancelClicked(this)
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

    private fun continueAddKeyword() {
        var proceed = true
        if (keywordET.text.isNullOrEmpty()) {
            keywordTIL.error = "키워드를 입력해주세요!"
            keywordTIL.isErrorEnabled = true
            proceed = false
        }

        if (proceed) {
            customWriteDialogInterface.onAddKeywordOkayClicked(this, keywordET.text.toString())
        }
    }


}