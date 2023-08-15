package com.soham.gdsc.firebaseAuth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignedInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update { it.copy(
            isSignInSuccessful = result.userData != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState(){
        _state.update { SignedInState() }
    }

}