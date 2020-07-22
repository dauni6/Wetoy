package com.dontsu.wetoy.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dontsu.wetoy.R
import com.dontsu.wetoy.model.User
import com.dontsu.wetoy.util.*
import com.dontsu.wetoy.view.activities.HomeActivity
import com.dontsu.wetoy.view.activities.LoginActivity
import com.dontsu.wetoy.view.fragments.UserFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.fragment_user.*

class UserInfoViewModel: ViewModel() {

    //firebase
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    var userImageUri = MutableLiveData<String>() // 이미지
    var userName = MutableLiveData<String>() // 이름
    var userEmail = MutableLiveData<String>() // 이메일

    //유저정보 초기화
    fun initializeUser() {
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {
                val person = it.toObject(User::class.java)
                person?.let {
                    userImageUri.value = person.userImageUri
                    userName.value = person.userName
                    userEmail.value = person.userEmail
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.e("UserInfoViewModel", "유저정보 초기화 에러 ${it.message}")
            }
    }

    //로그아웃
    fun requestLogout(fragment: Fragment) { // 나중에 이메일 유저인지 카톡, 네이버, 구글인지 when절로 잘 구분하기
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
        // DataBase 삭제 -> Storage 삭제 -> Authentication 삭제
        firebaseDB.collection(DATA_USERS).document(userId!!).delete().addOnCompleteListener{
            if (it.isSuccessful) {
                Log.d("UserInfoViewModel", "irebaseDB.collection(DATA_USERS).document(userId!!).delete() 삭제 성공")
                firebaseStorage.child(userId!!).child(userId!!).delete().addOnCompleteListener {
                    Log.d("UserInfoViewModel", "firebaseStorage.child(DATA_USERS_PROFILE_IMG_STORAGE).child(userId!!) 삭제 성공")
                    firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
                        Log.d("UserInfoViewModel", "유저 삭제 성공")
                        //firebaseAuth.signOut()
                        Toast.makeText(fragment.requireActivity(), "회원탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        fragment.userProgressLayout.visibility = View.GONE
                        val intent = Intent(fragment.requireActivity(), LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NO_HISTORY
                        fragment.startActivity(intent)
                        fragment.requireActivity().finish()
                    }?.addOnFailureListener {
                        it.printStackTrace()
                        Log.e("UserInfoViewModel", "회원탈퇴 에러 ${it.message}")
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Log.e("UserInfoViewModel", "firebaseStorage.child(DATA_USERS_PROFILE_IMG_STORAGE).child(userId!!) 삭제 실패 ${it.message}")
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
            Log.e("UserInfoViewModel", "firebaseDB.collection(DATA_USERS) 삭제 실패 ${it.message}")
        }


    }

    //프로필 사진 저장
    fun storeImage(imageUri: Uri?, fragment: Fragment) {
        imageUri?.let {
            val filepath = firebaseStorage.child(userId!!).child(userId)
            filepath.putFile(imageUri).addOnSuccessListener {
                filepath.downloadUrl.addOnSuccessListener {
                    val uri = it.toString()
                    firebaseDB.collection(DATA_USERS).document(userId).update(DATA_USERS_PROFILE_IMG, uri) //회원정보에 uri 업데이트
                        .addOnSuccessListener {
                            userImageUri.value = uri
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            Log.e("UserInfoViewModel", "프로필 사진 저장 실패 ${it.message}")
                            Toast.makeText(fragment.requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Log.e("UserInfoViewModel", "프로필 사진 저장 실패 ${it.message}")
                    Toast.makeText(fragment.requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                it.printStackTrace()
                Log.e("UserInfoViewModel", "프로필 사진 저장 실패 ${it.message}")
                Toast.makeText(fragment.requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //닉네임변경요청
    fun requestUserNameChange(fragment: Fragment){
        val customDialog = CustomUserNameChangeDialog(fragment.requireContext(), (fragment.requireActivity()) as HomeActivity)
        customDialog.show()
    }

    //닉네임변경
    fun userNameChangedComplete(name: String) {
        firebaseDB.collection(DATA_USERS).document(userId!!).update(DATA_USERS_USER_NAME, name)
            .addOnSuccessListener {
                userName.value = name
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.e("UserInfoViewModel", "닉네임 변경 실패 ${it.message}")
            }
    }

    //비밀번호변경요청
    fun requestPasswordChangeOnlyEmailUser(fragment: Fragment) {
        val customDialog = CustomUserPasswordChangeDialog(fragment.requireContext(), (fragment.requireActivity()) as HomeActivity)
        customDialog.show()
    }

    //비밀번호변경
    fun userPasswordChangedComplete(password: String) {
        firebaseAuth.currentUser?.updatePassword(password)!!.addOnCompleteListener {
            Log.d("UserInfoViewModel", "userPasswordChangedComplete() 비밀번호 변경 성공")
        }.addOnFailureListener {
            Log.e("UserInfoViewModel", "userPasswordChangedComplete() ${it.message} 비밀번호 변경 실패")
            it.printStackTrace()
        }
    }

    //푸시설정
    
    //건의 & 불편신고

}