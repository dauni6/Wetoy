package com.dontsu.wetoy.util

import android.Manifest
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dontsu.wetoy.R

val requiredPermissions = arrayListOf(
    Manifest.permission.CAMERA,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

//이미지 스피너
const val COLOR_SKIM = 0xFFA160FD //0xFF : 16진수
fun getProgressDrawable(context: Context): CircularProgressDrawable { //이미지 로딩시 스피너 보여주기
    return CircularProgressDrawable(context).apply {
        colorSchemeColors[0] = COLOR_SKIM.toInt()//스피너 색 변경
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

//유저 사진
fun ImageView.loadProfileUri(uri: String?, errorDrawable: Int = R.drawable.default_user) {
    val options = RequestOptions()
        .placeholder(getProgressDrawable(this.context))
        .error(errorDrawable)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

//게시글 이미지
fun ImageView.loadUri(uri: String?, errorDrawable: Int = R.drawable.default_user) {
    val options = RequestOptions()
        .error(errorDrawable)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

//유저 프로필 사진 보여주기
@BindingAdapter("android:imageUri")
fun loadImage(view: ImageView, uri: String?) {
    view.loadProfileUri(uri)
}