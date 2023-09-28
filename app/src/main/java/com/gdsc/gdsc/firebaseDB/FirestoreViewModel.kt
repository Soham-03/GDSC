package com.gdsc.gdsc.firebaseDB

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
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
            _state.value = _state.value.copy(isEventsLoading = false)
        }
    }
    suspend fun getAllEventsRefresh(){
        _state.value = _state.value.copy(isEventRefreshing = true)
        val eventsData = firestoreRepo.getAllEvents()
        if(eventsData.isEmpty()){
            _state.value = _state.value.copy(isEventFailed = "Failed")
        }
        else{
            _state.value = _state.value.copy(isEventsSuccess = eventsData)
            Handler(Looper.getMainLooper()).postDelayed({
                _state.value = _state.value.copy(isEventRefreshing = false)
            }, 1000)
        }
    }

    suspend fun registerForEvent(eventId: String, uid:String, eventName: String, eventTags: String){
        firestoreRepo.registerForEvent(eventId, uid, eventName, eventTags)
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

    suspend fun getLeaderboardTop10Refresh(){
        _state.value = _state.value.copy(isLeaderBoardRefreshing = true)
        val status = firestoreRepo.getLeaderBoardTop10()
        if(status.isNotEmpty()){
            _state.value = _state.value.copy(isLeaderBoardDataSuccess = status)
            Handler(Looper.getMainLooper()).postDelayed({
                _state.value = _state.value.copy(isLeaderBoardRefreshing = false)
            }, 1000)
        }
    }

    suspend fun getFlagshipEvents(){
        _state.value = _state.value.copy(isFlagShipEventLoading = true)
        val status = firestoreRepo.getFlagshipEvents()
        if(status.isNullOrEmpty()){
            _state.value = _state.value.copy(isFlagShipEventError = true)
        }
        else{
            _state.value = _state.value.copy(isFlagShipEventSuccess = status)
            _state.value = _state.value.copy(isFlagShipEventLoading = false)
        }
    }

    suspend fun getBestOfMonth(){
        val status = firestoreRepo.getBestOfMonth()
        _state.value = _state.value.copy(isBestOfMonthSuccess = status)
    }

    suspend fun getProblemStatement(){
        val status = firestoreRepo.getProblem()
        _state.value = _state.value.copy(isProblemStatementSuccess = status)
        println("Link"+status)
    }

    suspend fun getProblemStatementRefresh(){
        _state.value = _state.value.copy(isProblemStatementRefreshing = true)
        val status = firestoreRepo.getProblem()
        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = _state.value.copy(isProblemStatementRefreshing = false)
        }, 1000)
        println("Link"+status)
    }

    suspend fun getAttendedEvents(uid: String){
        val status = firestoreRepo.getAttendedEvents(uid)
        _state.value = _state.value.copy(isEventsAttendedSuccess = status)
    }

}