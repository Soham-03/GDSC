package com.gdsc.gdsc.firebaseDB

import com.gdsc.gdsc.model.AttendedEvent
import com.gdsc.gdsc.model.Event
import com.gdsc.gdsc.model.FlagShipEvent
import com.gdsc.gdsc.model.LeaderBoardData
import com.gdsc.gdsc.model.Problem

data class FirebaseState(
    val isUserDataAvailable: ArrayList<String?>? = null,
    val isUserDataFetchLoading: Boolean = true,
    //
    val isEventsLoading: Boolean = true,
    val isEventsSuccess: ArrayList<Event> = ArrayList(),
    val isEventFailed: String = "",
    val isEventRefreshing: Boolean = false,
    //
    val registeredEventExistStatus: Boolean = false,
    //
    val eventRegistrationStatus: String = "",
    //
    val isLeaderBoardDataSuccess: ArrayList<LeaderBoardData> = ArrayList(),
    val isLeaderBoardRefreshing: Boolean = false,
    //
    val isFlagShipEventLoading: Boolean = true,
    val isFlagShipEventSuccess: ArrayList<FlagShipEvent> = ArrayList(),
    val isFlagShipEventError:Boolean = false,
    //
    val isBestOfMonthSuccess: ArrayList<String> = ArrayList(),

    val isProblemStatementSuccess: Problem? = null,
    val isProblemStatementRefreshing: Boolean = false,

    val isEventsAttendedSuccess: ArrayList<AttendedEvent> = ArrayList()

)
