package com.example.medicineschedule.database

import android.util.Log
import android.widget.Toast
import com.example.medicineschedule.DictionaryActivity
import com.example.medicineschedule.HomeScreen
import com.example.medicineschedule.models.Drug
import com.example.medicineschedule.models.Drug.Companion.toDrug
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FirebaseServiceDrug {

    object FirebaseProfileService {

        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("Medicines")
        var list = ArrayList<String>()
        private const val TAG = "FirebaseProfileService"
        suspend fun getDrugeData(medId: String): Drug? {
            return try {
                collection.document(medId).get().await().toDrug()
            } catch (e: Exception) {
                Log.e(TAG, "Error getting user details", e)
//                FirebaseCrashlytics.getInstance().log("Error getting user details")
//                FirebaseCrashlytics.getInstance().setCustomKey("name", "Medicines")
//                FirebaseCrashlytics.getInstance().recordException(e)
                return Drug(
                    "Null",
                    "in Catch",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            }
        }
    }
}