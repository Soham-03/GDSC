package com.soham.gdsc.firebaseDB

import com.soham.gdsc.model.Event
import com.soham.gdsc.model.FlagShipEvent
import com.soham.gdsc.model.LeaderBoardData

data class FirebaseState(
    val isUserDataAvailable: ArrayList<String?>? = null,
    val isUserDataFetchLoading: Boolean = false,
    //
    val isEventsLoading: Boolean = false,
    val isEventsSuccess: ArrayList<Event> = ArrayList(),
    val isEventFailed: String = "",
    //
    val registeredEventExistStatus: Boolean = false,
    //
    val eventRegistrationStatus: String = "",
    //
    val isLeaderBoardDataSuccess: ArrayList<LeaderBoardData> = ArrayList(),
    //
    val isFlagShipEventLoading: Boolean = false,
    val isFlagShipEventSuccess: ArrayList<FlagShipEvent> = ArrayList(),
    val isFlagShipEventError:Boolean = false,
    //
    val isBestOfMonthSuccess: ArrayList<String> = ArrayList()
)
