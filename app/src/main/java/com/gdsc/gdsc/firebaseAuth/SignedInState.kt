package com.gdsc.gdsc.firebaseAuth

data class SignedInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
