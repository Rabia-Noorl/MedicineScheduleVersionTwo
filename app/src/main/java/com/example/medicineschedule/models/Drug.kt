package com.example.medicineschedule.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Drug(val drugId: String, //Document ID is actually the Drug id
                val name: String,
                val brands: String,
                val price: String,
                val manufacBy: String,
                val HowItsWork: String,
                val pharmaIngr: String,
                val sideEffects: String,
                val ab1: String,
                val ab2: String,
                val ab3: String,
                val ab4: String,
                val ab5: String,
                val prescribedFor: String,
                val medIntrerection: String,
                val whenNotUse: String,
                val warrnings: String,
                val missedDose: String,
                val overdose: String
                ) : Parcelable {

    companion object {
        fun DocumentSnapshot.toDrug(): Drug? {
            try {
                val name = getString("name")!!
                val brands = getString("brands")!!
                val price = getString("price")!!
                val manufacBy= getString("manufacBy")!!
                val howItsWork = getString("howItsWork")!!
                val pharmaIngr = getString("pharmaIngr")!!
                val sideEffects = getString("sideEffects")!!
                val ab1 = getString("ab1")!!
                val ab2 = getString("ab2")!!
                val ab3= getString("ab3")!!
                val ab4 = getString("ab4")!!
                val ab5= getString("ab5")!!
                val medIntrerection= getString("medIntrerection")!!
                val whenNotUse = getString("whenNotUse")!!
                val warrnings= getString("warrnings")!!
                val prescribedFor = getString("prescribedFor")!!
                val missedDose = getString("missedDose")!!
                val overdose = getString("overdose")!!

                return Drug(id,
                    name,
                    brands,
                    price,
                    manufacBy,
                    howItsWork,
                    pharmaIngr,
                    sideEffects,
                    ab1,
                    ab2,
                    ab3,
                    ab4,
                    ab5,
                    medIntrerection,
                    whenNotUse,
                    warrnings,
                    prescribedFor,
                    missedDose,
                    overdose)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting drug recode", e)
//                FirebaseCrashlytics.getInstance().log("Error converting drug record")
//                FirebaseCrashlytics.getInstance().setCustomKey("drugId", id)
//                FirebaseCrashlytics.getInstance().recordException(e)
                return null
            }
        }
        private const val TAG = "Drug"
    }
}
