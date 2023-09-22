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
