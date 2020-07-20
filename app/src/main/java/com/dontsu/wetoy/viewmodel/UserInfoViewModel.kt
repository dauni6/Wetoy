package com.dontsu.wetoy.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dontsu.wetoy.model.User
import com.dontsu.wetoy.util.DATA_USERS
import com.dontsu.wetoy.view.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_user.*

class UserInfoViewModel: ViewModel() {

    //firebase
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    var userImage = MutableLiveData<String>() //유저 이미지
    var userName = MutableLiveData<String>() //유저 이름
    var userEmail = MutableLiveData<String>() //이메일
    
    //HomeActivity로 들어오는 순간 유저정보 다 가져오기
    fun initializeUser() {
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                user?.let {
                    userImage.value = user?.userImageUri
                    userName.value = user?.userName
                    userEmail.value = user?.userEmail
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.e("UserInfoViewModel", "유저정보 초기화 에러 ${it.message}")
            }
    }

    //로그아웃
    //이메일 유저인지 카톡, 네이버, 구글인지 when절로 잘 구분하기
    fun requestLogout(fragment: Fragment) {
        fragment.userProgressLayout.visibility = View.VISIBLE
        //일단은 firebase로 하기
        firebaseAuth.signOut()
        val intent = Intent(fragment.requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NO_HISTORY
        fragment.startActivity(intent)
        fragment.requireActivity().finish()
        fragment.userProgressLayout.visibility = View.GONE
    }

    //회원탈퇴
    fun requestDeleteAccount(fragment: Fragment) {
        fragment.userProgressLayout.visibility = View.VISIBLE
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseAuth.signOut()
                //정보 다 삭제해주기
                firebaseDB.collection(DATA_USERS).document(userId!!).delete()
                    .addOnCompleteListener {
                        Toast.makeText(fragment.requireActivity(), "회원탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(fragment.requireActivity(), LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NO_HISTORY
                        fragment.startActivity(intent)
                        fragment.userProgressLayout.visibility = View.GONE
                        fragment.requireActivity().finish()
                    }

            }
        }?.addOnFailureListener {
            it.printStackTrace()
            Log.e("UserInfoViewModel", "회원탈퇴 에러 ${it.message}")
        }
    }

    //비밀번호변경

    //닉네임변경
    
    //푸시설정
    
    //건의 & 불편신고

}