package com.dontsu.wetoy.viewmodel

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dontsu.wetoy.model.User
import com.dontsu.wetoy.util.*
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

    var user = MutableLiveData<User>() //유저

    //유저정보 초기화
    fun initializeUser() {
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener {
                val person = it.toObject(User::class.java)
                person?.let {
                    user.value = person
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


    //프로필 사진 저장
    fun storeImage(imageUri: Uri?, fragment: Fragment) {
        imageUri?.let {
            val filepath = firebaseStorage.child(DATA_USERS_PROFILE_IMG_STORAGE).child(userId!!)
            filepath.putFile(imageUri).addOnSuccessListener {
                filepath.downloadUrl.addOnSuccessListener {
                    val uri = it.toString()
                    firebaseDB.collection(DATA_USERS).document(userId).update(DATA_USERS_PROFILE_IMG, uri) //회원정보에 uri 업데이트
                        .addOnSuccessListener {
                            user.value?.userImageUri = uri
                        }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Log.e("UserInfoViewModel", "프로필 사진 저장 에러 ${it.message}")
                    Toast.makeText(fragment.requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                it.printStackTrace()
                Log.e("UserInfoViewModel", "프로필 사진 저장 에러 ${it.message}")
                Toast.makeText(fragment.requireActivity(), "이미지 업로드 실패. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //닉네임변경
    fun requestUserNameChange(fragment: Fragment){
        AlertDialog.Builder(fragment.context!!)
            .setTitle("닉네임 변경하기")
            .setMessage("변경할 닉네임을 적어주세요!")
            .setPositiveButton("예") { dialog, which ->
                
            }
            .setNegativeButton("취소") { dialog, which -> }
            .show()
    }

    //비밀번호변경
    fun requestPasswordChangeOnlyEmailUser(fragment: Fragment) {

    }

    //푸시설정
    
    //건의 & 불편신고

}