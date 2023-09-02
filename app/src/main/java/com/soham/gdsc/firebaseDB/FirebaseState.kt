package com.soham.gdsc.firebaseDB

import com.soham.gdsc.model.AttendedEvent
import com.soham.gdsc.model.Event
import com.soham.gdsc.model.FlagShipEvent
import com.soham.gdsc.model.LeaderBoardData
import com.soham.gdsc.model.Problem

data class FirebaseState(
    val isUserDataAvailable: ArrayList<String?>? = null,
    val isUserDataFetchLoading: Boolean = true,
    //
    val isEventsLoading: Boolean = true,
    val isEventsSuccess: ArrayList<Event> = ArrayList(),
    val isEventFailed: String = "",
    //
    val registeredEventExistStatus: Boolean = false,
    //
    val eventRegistrationStatus: String = "",
    //
    val isLeaderBoardDataSuccess: ArrayList<LeaderBoardData> = ArrayList(),
    //
    val isFlagShipEventLoading: Boolean = true,
    val isFlagShipEventSuccess: ArrayList<FlagShipEvent> = ArrayList(),
    val isFlagShipEventError:Boolean = false,
    //
    val isBestOfMonthSuccess: ArrayList<String> = ArrayList(),

    val isProblemStatementSuccess: Problem? = null,

    val isEventsAttendedSuccess: ArrayList<AttendedEvent> = ArrayList()

)
