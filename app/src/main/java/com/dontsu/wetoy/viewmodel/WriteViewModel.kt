package com.dontsu.wetoy.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dontsu.wetoy.util.CustomWriteAddKeywordDialog

import com.dontsu.wetoy.view.activities.WriteActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.ArrayList

class WriteViewModel: ViewModel() {

    //firebase
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    var toyImageUri = MutableLiveData<String>() // 이미지
    var toyTitle = MutableLiveData<String>() // 제목
    var toyText = MutableLiveData<String>() // 글 내용
    var toyRegion = MutableLiveData<String>() // 지역
    var toyKeywords = MutableLiveData<ArrayList<String>>() //키워드
    var toyKeywordCount = MutableLiveData<String>() //키워드 갯수

    var keywordsArray = arrayListOf<String>() //임시 키워드 저장 배열

    init {
        toyKeywordCount.value = "0개" //키워드 갯수 초기화
    }

    //키워드 추가 요청
    fun requestAddKeyword(activity: WriteActivity) {
        val customDialog = CustomWriteAddKeywordDialog(activity, activity)
        customDialog.show()
    }

    //키워드 배열에 추가
    fun addKeywordArray(keyword: String) {
        keywordsArray.add(keyword)
        toyKeywords.value = keywordsArray

        Log.d("배열", "${toyKeywords.value?.size} $keyword")
        toyKeywordCount.value = "${toyKeywords.value?.size}개"
    }

}