package com.soham.gdsc.firebaseDB

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.soham.gdsc.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirestoreViewModel(private val firestoreRepo: FirestoreRepo): ViewModel() {
    private val _state = MutableStateFlow(FirebaseState())
    val state: StateFlow<FirebaseState> = _state
    suspend fun setUserData(user: FirebaseUser, collegeName: String, phoneNumber: String){
        firestoreRepo.setUserData(user,collegeName,phoneNumber)
    }
    suspend fun getUserData(uid: String){
        val userData = firestoreRepo.getUserCollege(uid)
        _state.value = _state.value.copy(isUserDataAvailable = userData)
    }

    suspend fun getAllEvents(){
        _state.value = _state.value.copy(isEventsLoading = true)
        val eventsData = firestoreRepo.getAllEvents()
        if(eventsData.isEmpty()){
            _state.value = _state.value.copy(isEventFailed = "Failed")
        }
        else{
            _state.value = _state.value.copy(isEventsSuccess = eventsData)
        }
        _state.value = _state.value.copy(isEventsLoading = false)
    }

    suspend fun registerForEvent(eventId: String, uid:String, eventName: String){
        firestoreRepo.registerForEvent(eventId, uid, eventName)
    }

    suspend fun checkEventInRegisteredList(eventId: String, uid:String){
        val status = firestoreRepo.checkEventInRegistered(uid, eventId)
        _state.value = _state.value.copy(registeredEventExistStatus = status)
    }

    suspend fun checkStatus(eventId: String, uid:String){
        val status = firestoreRepo.checkStatus(uid, eventId)
        _state.value = _state.value.copy(eventRegistrationStatus = status)
        println(status)
    }

    suspend fun getLeaderboardTop10(){
        val status = firestoreRepo.getLeaderBoardTop10()
        _state.value = _state.value.copy(isLeaderBoardDataSuccess = status)
    }

    suspend fun getFlagshipEvents(){
        _state.value = _state.value.copy(isFlagShipEventLoading = true)
        val status = firestoreRepo.getFlagshipEvents()
        if(status.isNullOrEmpty()){
            _state.value = _state.value.copy(isFlagShipEventError = true)
        }
        else{
            _state.value = _state.value.copy(isFlagShipEventSuccess = status)
        }
        _state.value = _state.value.copy(isFlagShipEventLoading = false)
    }

    suspend fun getBestOfMonth(){
        val status = firestoreRepo.getBestOfMonth()
        _state.value = _state.value.copy(isBestOfMonthSuccess = status)
        println("Another List:"+status)
    }

}