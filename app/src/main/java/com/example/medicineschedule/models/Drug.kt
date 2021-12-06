package com.example.medicineschedule.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Drug(val drugId: String, //Document ID is actually the Drug id
                val name: String,
                val brand: String,
                val strength: String,
                val type: String,
                val quantity: String,
                val price: String,
                val manufacturedBy: String,
                val activePharIngar: String,
                val alterBrand: ArrayList<String>,
                val dose: ArrayList<String>,
                val prescribedFor: ArrayList<String>,
                val sideEffects: ArrayList<String>,
                val warnings:ArrayList<String>,


                ) : Parcelable {

    companion object {
        fun DocumentSnapshot.toDrug(): Drug? {
            try {
                val name = getString("brandName")!!
                val brand = getString("howItsWork")!!
                val strength = getString("strength")!!
                val type= getString("type")!!
                val quantity= getString("size")!!
                val price= getString("price")!!
                val manufacturedBy= getString("manufacturedBy")!!
                val activePharIngar= getString("activePharmaIngar")!!
                val alterBrand= get("alterBrands")!!
                val dose= get("dose")!!
                val prescribedFor= get("prescribedFor")!!
                val sideEffects= get("sideEffects")!!
                val warnings= get("warnings")!!
                return Drug(id, name, brand,strength,type,quantity,price,manufacturedBy,activePharIngar,
                    alterBrand as ArrayList<String>,
                    dose as ArrayList<String>,
                    prescribedFor as ArrayList<String>,
                    sideEffects as ArrayList<String>,
                    warnings as ArrayList<String>
                )
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
