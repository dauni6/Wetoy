package com.dontsu.wetoy.util

import android.app.Dialog
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

class CustomUserNameChangeDialog: DialogFragment() {


    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.custom_dialog_username_change, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle("닉네임 변경하기")
            .setPositiveButton("예") { dialog, which ->  

            }
            .setNegativeButton("취소") {dialog, which ->  }

        return builder.create()
    }
}

/*public class CustomLoginDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_login_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(“Login”);
        builder.setView(view);

        final EditText emailText = view.findViewById(R.id.email);
        final EditText passwordText = view.findViewById(R.id.password);

        builder.setNegativeButton(“Cancel”, null);
        builder.setPositiveButton(“Login”, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(getActivity(), “Email is ” + emailText.getText().toString() + ” and password is” + passwordText.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    });

        return builder.create();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Toast.makeText(getActivity(), “Dialog Canceld”, Toast.LENGTH_SHORT).show();
    }
}*/
