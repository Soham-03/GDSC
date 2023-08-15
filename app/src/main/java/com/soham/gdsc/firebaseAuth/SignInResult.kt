package com.soham.gdsc.firebaseAuth

data class SignInResult(
    val userData: UserData?,
    val errorMessage:  String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?
)
