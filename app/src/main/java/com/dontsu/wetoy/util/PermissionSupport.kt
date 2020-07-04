package com.dontsu.wetoy.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionSupport(private val activity: Activity, private val context: Context) {

    /**
     * 위치, 카메라, 사진첩 권한 요청
     * */
    fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { //SDK 23버전 이하는 permission 필요없음
            //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
            var rejectedPermissionList = arrayListOf<String>()

            //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
            for(permission in requiredPermissions){
                if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    //만약 권한이 없다면 rejectedPermissionList에 추가
                    rejectedPermissionList.add(permission)
                }
            }
            //거절된 퍼미션이 있다면...
            if(rejectedPermissionList.isNotEmpty()){
                //권한 요청!
                val array = arrayOfNulls<String>(rejectedPermissionList.size)
                ActivityCompat.requestPermissions(activity, rejectedPermissionList.toArray(array), MULTI_PERMISSION_CODE)
            }
        }
    }
}