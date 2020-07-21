package com.dontsu.wetoy.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dontsu.wetoy.R
import com.dontsu.wetoy.databinding.FragmentUserBinding
import com.dontsu.wetoy.util.*
import com.dontsu.wetoy.viewmodel.UserInfoViewModel
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private lateinit var viewModel: UserInfoViewModel
    private var binding: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(UserInfoViewModel::class.java)
        view?.let {
            binding = DataBindingUtil.bind(view)
            binding!!.lifecycleOwner = requireActivity()
            binding!!.user = viewModel.user.value
        }

        info_requestUserLogout.setOnClickListener {
            viewModel.requestLogout(this@UserFragment)
        }

        info_requestUserDeleteAccount.setOnClickListener {
            viewModel.requestDeleteAccount(this@UserFragment)
        }

        info_userProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }

        info_userName.setOnClickListener {
            viewModel.requestUserNameChange(this@UserFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            viewModel.storeImage(data?.data, this@UserFragment)
        }
    }

}