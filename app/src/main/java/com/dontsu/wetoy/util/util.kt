package com.dontsu.wetoy.util

import android.Manifest
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dontsu.wetoy.R

val requiredPermissions = arrayListOf(
    Manifest.permission.CAMERA,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

fun ImageView.loadUri(uri: String?, errorDrawable: Int = R.drawable.default_user) {
    val options = RequestOptions()
        .error(errorDrawable)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}