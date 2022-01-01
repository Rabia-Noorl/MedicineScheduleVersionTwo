package com.example.medicineschedule.classes

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

public class MedicineSchedudle : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}