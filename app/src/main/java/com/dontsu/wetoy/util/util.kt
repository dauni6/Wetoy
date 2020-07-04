package com.dontsu.wetoy.util

import android.Manifest

const val MULTI_PERMISSION_CODE = 602
const val PERMISSION_CAMERA = 148
const val PERMISSION_ACCESS_FINE_LOCATION = 46
const val PERMISSION_WRITE_EXTERNAL_STORAGE = 169
const val PERMISSION_READ_EXTERNAL_STORAGE = 200

val requiredPermissions = arrayListOf(
    Manifest.permission.CAMERA,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)