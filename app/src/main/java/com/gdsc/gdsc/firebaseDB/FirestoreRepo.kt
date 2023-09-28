package com.gdsc.gdsc.firebaseDB

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.gdsc.gdsc.model.AttendedEvent
import com.gdsc.gdsc.model.Event
import com.gdsc.gdsc.model.FlagShipEvent
import com.gdsc.gdsc.model.LeaderBoardData
import com.gdsc.gdsc.model.Problem
import kotlinx.coroutines.tasks.await

class FirestoreRepo(
    private val context: Context
)
{
    val db = FirebaseFirestore.getInstance()
    suspend fun getUserCollege(uid: String): ArrayList<String?> {
        val data = ArrayList<String?>()
        val doc = db.collection("users").document(uid).get().await()
        val collegeName = doc.data?.get("userClass").toString()
        val tags = doc.data?.get("tags").toString()
        data.add(collegeName)
        data.add(tags)
        return data
    }

    suspend fun setUserData(myuser: FirebaseUser, collegeName:String,phoneNumber: String){
        val doc = db.collection("users").document(myuser.uid).get().await()
        val exists = doc.exists()
        if(!exists){
            val hashMap = HashMap<String, Any>()
            hashMap["userClass"] = collegeName.toString()
            hashMap["tags"] = 0
            hashMap["userName"] = myuser.displayName.toString()
            hashMap["userImage"] = myuser.photoUrl.toString()
            db.collection("users").document(myuser.uid)
                .set(hashMap).addOnSuccessListener {
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }

    suspend fun getProblem(): Problem{
        val docs = db.collection("problemOfWeek").document("problem").get().await()
        var problem = Problem("","","", "")
        if(docs.exists()){
               problem = Problem(docs["problemTitle"].toString(), docs["problemDesc"].toString(), docs["problemStartAndEnd"].toString(), submissionLink =  docs["link"].toString())
        }
        return problem
    }

    suspend fun getAllEvents(): ArrayList<Event> {
        val data = ArrayList<Event>()
        val docs = db.collection("events").orderBy("timestamp",Query.Direction.ASCENDING).get().await()
        for(doc in docs){
            data.add(
                Event(
                    eventId = doc.id,
                    eventName = doc["eventName"].toString(),
                    eventImage = doc["eventImage"].toString(),
                    eventDate = doc["eventDate"].toString(),
                    eventTime = doc["eventTime"].toString(),
                    eventTags = doc["eventTags"].toString(),
                    quizStatus = doc["quizStatus"] as Boolean,
                    upcoming = doc["upcoming"] as Boolean,
                    eventAbout = doc["eventAbout"].toString(),
                    eventLink = doc["eventLink"].toString()
                )
            )
            println("About"+doc["eventAbout"].toString())
        }
        return data
    }

    suspend fun registerForEvent(eventId: String, uid:String, eventName: String, eventTags: String){
        val hashMap = HashMap<String,String>()
        hashMap["eventName"] = eventName
        hashMap["status"] = "waiting"
        hashMap["tags"] = eventTags
        db.collection("users").document(uid).collection("eventsPendingConfirmation").document(eventId).set(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Registered for event, Waiting for confirmation", Toast.LENGTH_SHORT).show()
            }.await()
    }

    suspend fun checkEventInRegistered(uid: String, eventId: String): Boolean {
        val docs = db.collection("users").document(uid).collection("eventsPendingConfirmation")
            .document(eventId).get().await()
        return docs.exists()
    }

    suspend fun checkStatus(uid:String, eventId:String): String {
        var result = ""
        val docs = db.collection("users").document(uid).collection("eventsPendingConfirmation").document(eventId).get().await()
        result = if(docs["status"] == "waiting"){
            "Waiting for confirmation"
        } else if(docs["status"] == "confirmed"){
            "Confirmed"
        }
        else{
            "Register for the event"
        }
        return result
    }

    suspend fun getLeaderBoardTop10(): ArrayList<LeaderBoardData> {
        val list = ArrayList<LeaderBoardData>()
        val docs =
            db.collection("users").orderBy("tags", Query.Direction.DESCENDING).limit(10).get()
                .await()
        for (doc in docs) {
            list.add(
                LeaderBoardData(
                    tags = doc["tags"].toString(),
                    userName = doc["userName"].toString(),
                    userImage = doc["userImage"].toString(),
                    userClass = doc["userClass"].toString()
                )
            )
        }
        return ArrayList(list.sortedByDescending { it.tags.toInt() })
    }

    suspend fun getFlagshipEvents(): ArrayList<FlagShipEvent>{
        val list = ArrayList<FlagShipEvent>()
        val docs = db.collection("flagshipEvents").get().await()
        for(doc in docs){
            list.add(FlagShipEvent(eventName = doc["eventName"].toString(), eventImage = doc["eventImage"].toString()))
        }
        return list
    }

    suspend fun getBestOfMonth(): ArrayList<String>{
        val list = ArrayList<String>()
        val docs = db.collection("monthlyLeaderboard").orderBy("tags",Query.Direction.DESCENDING).limit(3).get().await()
        for(doc in docs){
            list.add(doc["userImage"].toString())
        }
        println("List: "+list)
        return list
    }

    suspend fun getAttendedEvents(uid: String): ArrayList<AttendedEvent>{
        val list = ArrayList<AttendedEvent>()
        val docs = db.collection("users").document(uid).collection("eventsAttended")
            .orderBy("timestamp").get().await()
        for(doc in docs){
            list.add(AttendedEvent(eventImg = doc["eventImage"].toString(), eventName = doc["eventName"].toString(), tagsEarned = doc["tagsEarned"].toString(), attendedOn = doc["attendedOn"].toString()))
        }
        return list
    }

}