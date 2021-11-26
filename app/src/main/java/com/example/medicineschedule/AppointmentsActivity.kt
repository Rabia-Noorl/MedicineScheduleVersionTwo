package com.example.medicineschedule

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.databinding.ActivityAddDoctorBinding
import com.example.medicineschedule.databinding.ActivityAppointmentsBinding
import com.example.medicineschedule.databinding.FragmentPharmacyBinding
import com.example.medicineschedule.viewModels.AppointmentViewModel
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.example.medicineschedule.viewModels.ReportViewModel

class AppointmentsActivity : AppCompatActivity() {

    lateinit var viewModel: AppointmentViewModel
    private lateinit var binding: ActivityAppointmentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)
        binding = ActivityAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            AppointmentViewModel::class.java)
        binding.appointmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.appointmentViewModel = viewModel
        }
        viewModel.allRemiders.observe(this){
            viewModel.addFun(it)
        }
    }
}