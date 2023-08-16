package com.soham.gdsc.firebaseDB

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.soham.gdsc.firebaseAuth.UserData
import kotlinx.coroutines.tasks.await

class UserRepo(
    val context: Context
)
{
    val db = FirebaseFirestore.getInstance()
    suspend fun getUserCollegeAndTags(uid: String): ArrayList<String?> {
        val data = ArrayList<String?>()
        var collegeName:String? = null
        var tags: String? = null
        db.collection("users").document(uid).get()
            .addOnSuccessListener {doc->
                collegeName = doc.data?.get("collegeName").toString()
                tags = doc.data?.get("tags").toString()
            }
            .await()
        data.add(collegeName)
        data.add(tags)
        return data
    }

    suspend fun setUserData(uid:String, collegeName:String){
        val hashMap = HashMap<String, String>()
        hashMap["collegeName"] = collegeName
        hashMap["tags"] = "0"
        db.collection("users").document(uid)
            .set(hashMap).addOnSuccessListener {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
            }

    }

}