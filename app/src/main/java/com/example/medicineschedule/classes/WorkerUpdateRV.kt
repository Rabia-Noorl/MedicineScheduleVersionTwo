package com.example.medicineschedule.classes

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.medicineschedule.viewModels.HomeRecViewModel

class WorkerUpdateRV (appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams)
{
    lateinit var hrvm:HomeRecViewModel
    override fun doWork(): Result {
        // Do the work here--in this case, upload the images.
        d()
        Log.d("worker", "Working nowwwwwwwwwwwwwwwww")
        // Indicate whether the work finished successfully with the Result
        return Result.failure()
    }

    fun d(){

        try {
                        hrvm = HomeRecViewModel(Application())
            Log.d("wor", "working from function now")
        } catch(ex:Exception){
            print(ex)
        }

//        CoroutineScope(Dispatchers.Main).launch{
//            hrvm = HomeRecViewModel(Application())
//            Log.d("wor", "working from function now")
//        }
    }
}