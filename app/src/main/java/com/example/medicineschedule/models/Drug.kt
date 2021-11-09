package com.example.medicineschedule.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Drug(val drugId: String, //Document ID is actually the Drug id
                val name:String,
                val brand:String,
                val strength:String,
                val type:String,
                val quantity:String,
                val price:String,
                val manufacturedBy:String, val activePharIngar:String) : Parcelable
//    {
//
//    companion object {
//        fun DocumentSnapshot.toDrug(): Drug? {
//            try {
//                val name = getString("name")!!
//                val brand = getString("brand")!!
//                val strength = getString("strength")!!
//                val type= getString("type")!!
//                val quantity= getString("quantity")!!
//                val price= getString("price")!!
//                val manufacturedBy= getString("manufacturedBy")!!
//                val activePharIngar= getString("activePharIngar")!!
//                return Drug(id,name, brand,strength,type,quantity,price,manufacturedBy,activePharIngar)
//            } catch (e: Exception) {
//                Log.e(TAG, "Error converting drug recode", e)
////                FirebaseCrashlytics.getInstance().log("Error converting drug record")
////                FirebaseCrashlytics.getInstance().setCustomKey("drugId", id)
////                FirebaseCrashlytics.getInstance().recordException(e)
//                return null
//            }
//        }
//        private const val TAG = "Drug"
//    }
//}



//data class Drug(
//    val name: String,
//    val dose: String,
//    val status: String,
//    val type: String
//)
//
//val Drug_List: List<Drug> =
//    listOf(
//        Drug(
//            "Pills", "Once a Day", "notTaken", "pills"
//        ),
//        Drug(
//            "Injection", "one at 8:pm", "notTaken", "syringe"
//        ),
//        Drug(
//            "Caps", "Two at 8:pm", "notTaken", "capsole"
//        ),
//        Drug(
//            "Caps", "Two at 8:pm", "notTaken", "capsole"
//        ),
//        Drug(
//            "Pills", "Once a Day", "notTaken", "pills"
//        ),
//        Drug(
//            "Pills", "Once a Day", "notTaken", "pills"
//        ),
//        Drug(
//            "Injection", "one at 8:pm", "notTaken", "syringe"
//        ),
//        Drug(
//            "Caps", "Two at 8:pm", "notTaken", "capsole"
//        ),
//        Drug(
//            "Caps", "Two at 8:pm", "notTaken", "capsole"
//        ),
//        Drug(
//            "Pills", "Once a Day", "notTaken", "pills"
//        )
//
//    )
//
