package com.soham.gdsc.firebaseAuth

data class SignedInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
