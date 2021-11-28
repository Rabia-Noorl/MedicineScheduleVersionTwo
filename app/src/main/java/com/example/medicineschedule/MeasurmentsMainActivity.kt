package com.example.medicineschedule

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
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
        viewModel.recodeCliked.observe(this){
            dialogueHandler(it)
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

    private fun dialogueHandler(reminderTracker: ReminderTracker){
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        var d : Dialog? = this.let { Dialog(it) }
        d?.setContentView(R.layout.edit_record_dialoge)
        d!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_design);
        d!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        d?.show()

        var name = d?.findViewById<TextView>(R.id.nameEditTV)
        var img = d?.findViewById<ImageView>(R.id.dialogeImgView)

        var updateBtn = d?.findViewById<Button>(R.id.updatebutton2)
        var deleteBtn = d?.findViewById<Button>(R.id.deletebutton3)

        img.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                R.drawable.ic_measurementwhite // Drawable
            )
        )
        name.setText(reminderTracker.names)

        updateBtn.setOnClickListener {
            val intent = Intent(this, AddMeasurements::class.java)
            intent.putExtra("rem", reminderTracker)
            startActivity(intent)
        }

        deleteBtn.setOnClickListener{
            var rem = ReminderTracker(
                reminderTracker.reminderType,
                reminderTracker.types,
                reminderTracker.names,
                reminderTracker.dateTimes,
                reminderTracker.status,
                reminderTracker.quantity,
                reminderTracker.instructions,
                reminderTracker.strenght,
                reminderTracker.startDate,
                reminderTracker.endDate,
                reminderTracker.recodeCreationDate,
                true
            )
            rem.id = reminderTracker.id
            viewModel.onEditClick(rem)
            d?.cancel()
        }
    }
}