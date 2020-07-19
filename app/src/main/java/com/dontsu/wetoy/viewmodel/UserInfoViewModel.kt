package com.dontsu.wetoy.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel: ViewModel() {
    var userImage = MutableLiveData<Uri>() //유저 이미지

}