package com.dontsu.wetoy.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseReference {
    //SignupActivity 에서는 잘 되는데 왜 UserFragment에서는 싱글톤 참조가 안될까???

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDB = FirebaseFirestore.getInstance()
    val firebaseStorage = FirebaseStorage.getInstance().reference
    val userId = FirebaseAuth.getInstance().currentUser?.uid

}