package com.soham.gdsc.model

data class Event(
    val eventId: String,
    var eventName: String,
    val eventDate: String,
    val eventTime: String,
    val eventImage: String,
    val eventTags: String,
    val eventAbout: String,
    val quizStatus: Boolean,
    val upcoming: Boolean,
    val eventLink: String
)
