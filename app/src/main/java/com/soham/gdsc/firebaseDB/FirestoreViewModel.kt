package com.soham.gdsc.firebaseDB

import androidx.lifecycle.ViewModel
import com.soham.gdsc.firebaseAuth.UserData

class FirestoreViewModel(private val firestoreRepo: UserRepo): ViewModel() {
    suspend fun setUserData(uid: String, collegeName: String){
        firestoreRepo.setUserData(uid,collegeName)
    }
}