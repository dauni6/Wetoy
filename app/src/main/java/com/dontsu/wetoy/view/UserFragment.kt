package com.dontsu.wetoy.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.dontsu.wetoy.util.FirebaseReference.firebaseAuth
import com.dontsu.wetoy.util.FirebaseReference.firebaseDB
import com.dontsu.wetoy.util.FirebaseReference.firebaseStorage
import com.dontsu.wetoy.util.FirebaseReference.userId
import com.dontsu.wetoy.viewmodel.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

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
            binding!!.viewModel = viewModel
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            storeImage(data?.data)
        }
    }

    //프로필 사진 저장하기
    private fun storeImage(imageUri: Uri?) {
        imageUri?.let {
            val filepath = firebaseStorage.child(DATA_USERS_PROFILE_IMG_STORAGE).child(userId!!)
            filepath.putFile(imageUri).addOnSuccessListener {
                filepath.downloadUrl.addOnSuccessListener {
                    val uri = it.toString()
                    firebaseDB.collection(DATA_USERS).document(userId).update(DATA_USERS_PROFILE_IMG, uri) //회원정보에 uri 업데이트
                        .addOnSuccessListener {
                            viewModel.userImage.value = uri
                            info_userProfile.loadUri(uri)
                        }
                }
                    .addOnFailureListener {
                        it.printStackTrace()
                        Toast.makeText(requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    }
            }
                .addOnFailureListener{
                    it.printStackTrace()
                    Toast.makeText(requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun requestPasswordChangeOnlyEmailUser() {

    }

}