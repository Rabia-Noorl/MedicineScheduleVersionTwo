package com.example.medicineschedule.classes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ontimeRec  : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"from one time receiver", Toast.LENGTH_SHORT).show()
    }
}