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
import com.dontsu.wetoy.viewmodel.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private lateinit var viewModel: UserInfoViewModel
    private var binding: FragmentUserBinding? = null

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

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
            requestLogout()
        }

        info_requestUserDeleteAccount.setOnClickListener {
            requestDeleteAccount()
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

    //이메일 유저인지 카톡, 네이버, 구글인지 when절로 잘 구분하기
    private fun requestLogout() {
        userProgressLayout.visibility = View.VISIBLE
        //일단은 firebase로 하기
        firebaseAuth.signOut()
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        requireActivity().finish()
        userProgressLayout.visibility = View.GONE
    }
    
    //회원탈퇴
    private fun requestDeleteAccount() {
        userProgressLayout.visibility = View.VISIBLE
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                //정보 다 삭제해주기
                firebaseDB.collection(DATA_USERS).document(userId!!).delete()
                    .addOnCompleteListener {
                        firebaseAuth.signOut()
                        Toast.makeText(requireActivity(), "회원탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NO_HISTORY
                        startActivity(intent)
                        requireActivity().finish()
                    }
                userProgressLayout.visibility = View.GONE
            }
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
                            viewModel.userImage.value = imageUri
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