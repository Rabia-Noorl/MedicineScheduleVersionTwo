package com.example.medicineschedule.classes

import android.app.Application
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import com.example.medicineschedule.R
import com.example.medicineschedule.dictionary.MedicationSearchFragment
import java.io.*


public class MedicineSchedudle : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }
}
