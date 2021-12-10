package com.example.medicineschedule.database

import android.util.Log
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.Drug.Companion.toDrug
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseServiceDrug {
    object FirebaseProfileService {
        var list = ArrayList<String>()
        private const val TAG = "FirebaseProfileService"
         suspend fun getDrugeData(userId: String): Drug? {
            val db = FirebaseFirestore.getInstance()
            return try {
                db.collection("Medicines")
                    .document(userId).get().await().toDrug()
            } catch (e: Exception) {
                Log.e(TAG, "Error getting user details", e)
//                FirebaseCrashlytics.getInstance().log("Error getting user details")
//                FirebaseCrashlytics.getInstance().setCustomKey("name", "Medicines")
//                FirebaseCrashlytics.getInstance().recordException(e)
                return Drug("Null","in Catch","","","","","","","","","","","","","","","","","")
          //  null
            }
        }
    }
}