
package com.example.medicineschedule

import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddMeasurementsBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddMeasurements : AppCompatActivity() {
    lateinit var viewModel: HomeRecViewModel
    lateinit var binding: ActivityAddMeasurementsBinding
    lateinit var alertdialogbuilder:AlertDialog.Builder
    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)
    var mNameindex=0
    var mInsindex=0
    var mTimeindex=0

    companion object{
        val timeListMeasurement = ArrayList<String>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeasurementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val record: ReminderTracker? = intent.getSerializableExtra("mes") as ReminderTracker?
        binding.measurement.setText(record?.names)
        binding.measureQuantity.setText(record?.quantity)
        binding.txtvwMtime1.setText(record?.dateTimes)

        var measurementsChoice= arrayOf("Blood Pressure", "Blood Sugar", "Weight","Temperature","Heart Rate", "Other")
        var selectedMeasurement=measurementsChoice[mNameindex]
        binding.measurement.setText(selectedMeasurement)
        var instructionChoice =
            arrayOf("Before Eating", "After Eating", "While Eating", "Doesn't Matter", "Other")
        var selectedIns=instructionChoice[mInsindex]
        binding.MInstructions.setText(selectedIns)
        var mtimeChoice= arrayOf("Once a day","2 times a day","3 times a day","4 times a day","5 times a day","6 times a day","7 times a day")
        var selectedTime=mtimeChoice[mTimeindex]
        binding.txtvwMtime.setText(selectedTime)
        binding.txtvwMtime1.visibility= VISIBLE
        var mtimeFormat= SimpleDateFormat("hh:mm a", Locale.US)
        var dateFormat=SimpleDateFormat("dd MMM yyyy",Locale.US)
       //set spinners
        var units=arrayOf("units")
        val adap=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        var unitsChoice = arrayOf("sys/dia/pulse")
        val adapter=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.mspinner.adapter=adapter

        var unitsChoice1=arrayOf("mg/dL","mmol/L")
        val adapter1=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice2=arrayOf("kg","lbs")
        val adapter2=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice3=arrayOf("celsius","fahrenheit")
        val adapter3=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice3)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice4=arrayOf("bpm")
        val adapter4=ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice4)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            HomeRecViewModel::class.java)
        binding.saveMeasurement.setOnClickListener{
            if(record == null){
                addMeasrementReminder()
            }else{
                var Name = binding.measurement.text.toString()
                var time = binding.txtvwMtime1.text
                if ( Name.isNotEmpty() && time.isNotEmpty()){
                    var reminder = ReminderTracker("mes","${record.types}", "$Name", "$time","${record.status}", "${record.quantity}","${record.instructions}","${record.strenght}","${record.startDate}","${record.endDate}", "${record.recodeCreationDate}", record.deleteFlage)
                    reminder.id = record.id
                    viewModel.onEditClick(reminder)
//                    val intent = Intent(this, MedicationFragment::class.java)
//                    startActivity(intent)
                    finish()
                }else{
                }
            }
        }
        binding.backArrowMeasurment.setOnClickListener{
            alertdialogbuilder=AlertDialog.Builder(this)
            alertdialogbuilder.setCancelable(false)
            alertdialogbuilder.setTitle("Confirm Exit!!!").setIcon(R.drawable.exitapp)
                .setMessage("Are you sure you want to Exit?").setCancelable(true).setPositiveButton("Yes"){dialogInterface,it->
                    this.finish()
                }
                .setNegativeButton("No"){dialogInterface,it->
                    dialogInterface.cancel()
                }.show()
        }

        binding.measurement.setOnClickListener(View.OnClickListener {
            var alertDialogDose = AlertDialog.Builder(this)
            alertDialogDose.setTitle("Select Measurement")
            alertDialogDose.setSingleChoiceItems(
                measurementsChoice,
               mNameindex
            ) { dialogInterface: DialogInterface, position: Int ->
                mNameindex=position
                binding.measurement.setText(measurementsChoice[position])
                if(position==0)
                {
                    binding.mspinner.adapter=adapter
                }
                if(position==1)
                {
                    binding.mspinner.adapter=adapter1
                }
                if(position==2)
                {
                    binding.mspinner.adapter=adapter2
                }
                if(position==3)
                {
                    binding.mspinner.adapter=adapter3
                }
                if(position==4)
                {
                    binding.mspinner.adapter=adapter4
                }
                if (position == 5) {
                    binding.mspinner.adapter=adap
                    binding.measurementName.visibility = View.VISIBLE
                }
                else{
                    binding.measurementName.visibility = View.GONE
                }

                dialogInterface.dismiss()
            }
            alertDialogDose.show()
        })
        binding.MInstructions.setOnClickListener(View.OnClickListener {
            var alertDialogIns = AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instructions")
            alertDialogIns.setSingleChoiceItems(
                instructionChoice,
                mInsindex
            ) { dialogInterface: DialogInterface, position: Int ->
                mInsindex=position
                binding.MInstructions.setText(instructionChoice[position])
                if (position == 4) {
                    binding.customMinstruction.visibility = View.VISIBLE
                }
                else{
                    binding.customMinstruction.visibility = View.GONE
                }
                dialogInterface.dismiss()
            }
            alertDialogIns.show()
        })

        binding.txtvwMtime.setOnClickListener(View.OnClickListener {
            var alertDialogtime=AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(mtimeChoice,mTimeindex){dialogInterface:DialogInterface,position:Int->
                mTimeindex=position
                binding.txtvwMtime.setText(mtimeChoice[position])

                when(position){
                    0 -> { binding.txtvwMtime1.visibility= View.VISIBLE
                        binding.txtvwMtime2.visibility = View.GONE
                        binding.txtvwMtime3.visibility = View.GONE
                        binding.txtvwMtime4.visibility = View.GONE
                        binding.txtvwMtime5.visibility=View.GONE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE
                    }
                    1 -> {binding.txtvwMtime1.visibility= View.VISIBLE
                        binding.txtvwMtime2.visibility=View.VISIBLE
                        binding.txtvwMtime3.visibility = View.GONE
                        binding.txtvwMtime4.visibility = View.GONE
                        binding.txtvwMtime5.visibility=View.GONE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE}
                    2 -> {
                        binding.txtvwMtime1.visibility = View.VISIBLE
                        binding.txtvwMtime2.visibility=View.VISIBLE
                        binding.txtvwMtime3.visibility=View.VISIBLE
                        binding.txtvwMtime4.visibility = View.GONE
                        binding.txtvwMtime5.visibility=View.GONE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE
                    }
                    3 -> {
                        binding.txtvwMtime1.visibility = View.VISIBLE
                        binding.txtvwMtime2.visibility=View.VISIBLE
                        binding.txtvwMtime3.visibility=View.VISIBLE
                        binding.txtvwMtime4.visibility=View.VISIBLE
                        binding.txtvwMtime5.visibility=View.GONE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE
                    }
                    4->{
                        binding.txtvwMtime1.visibility = View.VISIBLE
                        binding.txtvwMtime2.visibility=View.VISIBLE
                        binding.txtvwMtime3.visibility=View.VISIBLE
                        binding.txtvwMtime4.visibility=View.VISIBLE
                        binding.txtvwMtime5.visibility=View.VISIBLE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE

                    }
                    5->{
                        binding.txtvwMtime1.visibility =VISIBLE
                        binding.txtvwMtime2.visibility=VISIBLE
                        binding.txtvwMtime3.visibility=VISIBLE
                        binding.txtvwMtime4.visibility=VISIBLE
                        binding.txtvwMtime5.visibility=VISIBLE
                        binding.txtvwMtime6.visibility= VISIBLE
                        binding.txtvwMtime7.visibility=View.GONE
                    }
                    6->{
                        binding.txtvwMtime1.visibility =VISIBLE
                        binding.txtvwMtime2.visibility=VISIBLE
                        binding.txtvwMtime3.visibility=VISIBLE
                        binding.txtvwMtime4.visibility=VISIBLE
                        binding.txtvwMtime5.visibility=VISIBLE
                        binding.txtvwMtime6.visibility= VISIBLE
                        binding.txtvwMtime7.visibility= VISIBLE

                    }
                    else -> {
                        binding.txtvwMtime2.visibility = View.GONE
                        binding.txtvwMtime3.visibility = View.GONE
                        binding.txtvwMtime4.visibility = View.GONE
                        binding.txtvwMtime5.visibility=View.GONE
                        binding.txtvwMtime6.visibility=View.GONE
                        binding.txtvwMtime7.visibility=View.GONE

                    }
                }


                dialogInterface.dismiss()
            }
            alertDialogtime.show()
        })
        binding.txtvwMtime1.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime1.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime=Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtvwMtime1.setText(mtimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwMtime2.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime2.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwMtime2.setText(mtimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwMtime3.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime3.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwMtime3.setText(mtimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwMtime4.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime4.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwMtime4.setText(mtimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwMtime5.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime5.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwMtime5.setText(mtimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwMtime6.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=mtimeFormat.parse(binding.txtvwMtime6.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwMtime6.setText(mtimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
//        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,doseChoice)
//        binding.dosequantityunit.setAdapter(adapter)
//        binding.dosequantityunit.setThreshold(2)
//        binding.dosequantityunit.setOnClickListener(View.OnClickListener {
//            binding.dosequantityunit.showDropDown()


//        })

    }
//    private fun addMeasrementReminder() {
//        var name = binding.measurement.text.toString()
//        var quantity = binding.measureQuantity.text.toString()
//        var instructions = binding.MInstructions.text.toString()
//        var time = binding.txtvwMtime1.text.toString()
//
//        if ( name.length >  0 && quantity.length > 0 && time.length > 0 ){
//
//            var remider = ReminderTracker("mes",
//                "$name" ,"$time" ,
//                "Take", "$quantity ",
//                "Instruction: $instructions",
//                "","","", "${Calendar.getInstance().time}",false)
//            viewModel.onAddClick(remider)
//            val intent = Intent(this, HomeScreen::class.java)
//            startActivity(intent)
//            finish()
//
//        }else{
//            Toast.makeText(this, "Mandatory fields are missing" , Toast.LENGTH_SHORT).show()
//        }
//    }
override fun onBackPressed() {
    alertdialogbuilder=AlertDialog.Builder(this)
    alertdialogbuilder.setCancelable(false)
    alertdialogbuilder.setTitle("Confirm Exit!!!").setIcon(R.drawable.exitapp)
        .setMessage("Are you sure you want to Exit?").setCancelable(true).setPositiveButton("Yes"){dialogInterface,it->
            this.finish()
        }
        .setNegativeButton("No"){dialogInterface,it->
            dialogInterface.cancel()
        }.show()
}
private fun addMeasrementReminder() {
    val sdf = SimpleDateFormat("EE MMM dd yyyy 'at:' hh:mm a ")
    val currentDate = sdf.format(Date())

    timeListMeasurement.clear()
    var time = binding.txtvwMtime1.text.toString()
    var time1 = binding.txtvwMtime2.text.toString()
    var time2 = binding.txtvwMtime3.text.toString()
    var time3 = binding.txtvwMtime4.text.toString()
    var time4 = binding.txtvwMtime5.text.toString()
    var time5 = binding.txtvwMtime6.text.toString()
    var time6 = binding.txtvwMtime7.text.toString()
    timeListMeasurement.add(time)
    timeListMeasurement.add(time1)
    timeListMeasurement.add(time2)
    timeListMeasurement.add(time3)
    timeListMeasurement.add(time4)
    timeListMeasurement.add(time5)
    timeListMeasurement.add(time6)

    var name = binding.measurement.text.toString()
    var quantity = binding.measureQuantity.text.toString()
    var instructions = binding.MInstructions.text.toString()
    var type = binding.tvMeasureValue.text.toString()
    var measurmentUnit  = binding.mspinner.selectedItem.toString()

    timeListMeasurement.forEach {
        if ( name.length >  0 && quantity.length > 0 && it.length > 0 ){
            var remider = ReminderTracker("mes",
                "",
                "$name" ,
                "$it" ,
                "",
                "$quantity $measurmentUnit",
                "Instruction: $instructions",
                "","${Calendar.getInstance().time}",
                "",
                "$currentDate",
                false)
            viewModel.onAddClick(remider)
        }
        else{
        }
    }
    finish()
}
}
