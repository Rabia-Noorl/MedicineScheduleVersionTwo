package com.example.medicineschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAppointmentsBinding
import com.example.medicineschedule.databinding.ActivityMeasurmentsMainBinding
import com.example.medicineschedule.viewModels.AppointmentViewModel
import com.example.medicineschedule.viewModels.MeasurmentViewModel

class MeasurmentsMainActivity : AppCompatActivity() {

    lateinit var viewModel: MeasurmentViewModel
    private lateinit var binding: ActivityMeasurmentsMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measurments_main)

        binding = ActivityMeasurmentsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMesButton.setOnClickListener {
            val intent = Intent(this, AddMeasurements::class.java)
            startActivity(intent)
            finish()
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            MeasurmentViewModel::class.java)
        binding.measurmentViewModel= viewModel
        binding.let {
            it.lifecycleOwner = this
            it.measurmentViewModel = viewModel
        }
        viewModel.allRemiders.observe(this){
            it?.let {
                var recViewList = ArrayList<ReminderTracker>()
                it.forEach{
                    if(it.reminderType == "mes" && !it.deleteFlage)
                    {
                        recViewList.add(it)
                    }
                }
                viewModel.addFun(recViewList)
                var anim  = binding.measurmentLottieanim
                var initialText  = binding.initialTV
                anim.isVisible = recViewList.isEmpty()
                initialText.isVisible = recViewList.isEmpty()
            }
        }

    }
}