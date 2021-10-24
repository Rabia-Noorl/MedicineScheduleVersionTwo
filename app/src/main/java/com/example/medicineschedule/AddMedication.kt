//package com.example.medicineschedule
//
//import android.app.DatePickerDialog
//import android.app.TimePickerDialog
//import android.content.DialogInterface
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.MenuItem
//import android.view.View
//import android.widget.ArrayAdapter
//import android.widget.AutoCompleteTextView
//import android.widget.TimePicker
//import android.widget.Toast
//import androidx.appcompat.app.AlertDialog
//import com.example.medicineschedule.databinding.ActivityAddMedicationBinding
//import java.lang.Exception
package com.example.medicineschedule

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.medicineschedule.databinding.ActivityAddMedicationBinding

class AddMedication : AppCompatActivity() {
    lateinit var binding: ActivityAddMedicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var doseChoice = arrayOf(
            "Tablets",
            "Pills",
            "Injections",
            "Solutions",
            "Powders",
            "Drops",
            "Inhaler",
            "Other"
        )
        var instructionChoice =
            arrayOf("Before Eating", "After Eating", "While Eating", "Doesn't Matter", "Other")
        binding.dosequantityunit.setOnClickListener(View.OnClickListener {
            var alertDialogDose = AlertDialog.Builder(this)
            alertDialogDose.setTitle("Select Dose")
            alertDialogDose.setSingleChoiceItems(
                doseChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.dosequantityunit.setText(doseChoice[position])
                if (position == 7) {
                    binding.customdose.visibility = View.VISIBLE
                }
                else{
                    binding.customdose.visibility = View.GONE
                }

                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogDose.show()
        })
        binding.Instructions.setOnClickListener(View.OnClickListener {
            var alertDialogIns = AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instructions")
            alertDialogIns.setSingleChoiceItems(
                instructionChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.Instructions.setText(instructionChoice[position])
                if (position == 4) {
                    binding.custominstruction.visibility = View.VISIBLE
                }
                else{
                    binding.custominstruction.visibility = View.GONE
                }
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogIns.show()
        })
//        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,doseChoice)
//        binding.dosequantityunit.setAdapter(adapter)
//        binding.dosequantityunit.setThreshold(2)
//        binding.dosequantityunit.setOnClickListener(View.OnClickListener {
//            binding.dosequantityunit.showDropDown()


//        })

    }
}
//import java.sql.Time
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class AddMedication : AppCompatActivity() {
////    lateinit var binding:ActivityAddMedicationBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        binding= ActivityAddMedicationBinding.inflate(layoutInflater)
////        setContentView(binding.root)
////        var doseChoice= arrayOf("Pill","Injection","Solution","Powder","Drops","Inhaler","Other")
////        var instructionChoice= arrayOf("Before Eating","After Eating","While Eating","Doesn't Matter")
////        var timeChoice= arrayOf("Once a day","2 times a day","3 times a day","4 times a day","5 times a day","6 times a day","7 times a day")
////        var  thour:Int
////        var tmint:Int
////        var timeFormat=SimpleDateFormat("hh:mm a",Locale.US)
////        var dateFormat=SimpleDateFormat("dd MMM YY",Locale.US)
////        binding.txtvwdose.setOnClickListener(View.OnClickListener {
////            var alertDialogDose=AlertDialog.Builder(this)
////            alertDialogDose.setTitle("Select Dose")
////            alertDialogDose.setSingleChoiceItems(doseChoice,0){dialogInterface:DialogInterface,position:Int->
////                binding.txtvwdose.setText(doseChoice[position])
////
////                dialogInterface.dismiss()
//////                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
////            }
////            alertDialogDose.show()
////        })
////
////        binding.txtvwInstructions.setOnClickListener(View.OnClickListener {
////
////            var alertDialogIns=AlertDialog.Builder(this)
////            alertDialogIns.setTitle("Select Instructions")
////            alertDialogIns.setSingleChoiceItems(instructionChoice,0){dialogInterface:DialogInterface,position:Int->
////                binding.txtvwInstructions.setText(instructionChoice[position])
////                dialogInterface.dismiss()
//////                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
////            }
////            alertDialogIns.show()
////        })
////
////        binding.txtvwTime.setOnClickListener(View.OnClickListener {
////
////            var alertDialogtime=AlertDialog.Builder(this)
////            alertDialogtime.setTitle("How many times per day?")
////            alertDialogtime.setSingleChoiceItems(timeChoice,0){dialogInterface:DialogInterface,position:Int->
////                binding.txtvwTime.setText(timeChoice[position])
//////                binding.scrollView.visibility=View.VISIBLE
//////                binding.linearlayout2.visibility=View.VISIBLE
////                dialogInterface.dismiss()
//////                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
////            }
////            alertDialogtime.show()
////        })
////        binding.switchReminder.setOnClickListener(View.OnClickListener {
////            if(binding.switchReminder.isChecked)
////            {
////                binding.txtipTime.visibility=View.VISIBLE
////                binding.scrollView.visibility=View.VISIBLE
////                binding.linearlayout2.visibility=View.VISIBLE
//////                binding.txtstdate.visibility=View.VISIBLE
//////                binding.txtenddate.visibility=View.VISIBLE
////            }
////            else{
////                binding.txtipTime.visibility=View.GONE
////                binding.scrollView.visibility=View.GONE
////                binding.linearlayout2.visibility=View.GONE
////
//////                binding.txtstdate.visibility=View.GONE
//////                binding.txtenddate.visibility=View.GONE
////
////
////            }
////        })
////        //Setting time format
////        binding.txtTime1.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////           try{
////            var date=timeFormat.parse(binding.txtTime1.text.toString())
////            calendar.time=date}
////           catch (e:Exception){
////               e.printStackTrace()
////           }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime1.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime2.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime2.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime2.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime3.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime3.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime3.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime4.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime4.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime4.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime5.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime5.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime5.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime6.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime6.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime6.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        binding.txtTime7.setOnClickListener(View.OnClickListener {
////            var calendar= Calendar.getInstance()
////            try{
////                var date=timeFormat.parse(binding.txtTime7.text.toString())
////                calendar.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
////                var selectedTime=Calendar.getInstance()
////                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
////                selectedTime.set(Calendar.MINUTE,minute)
////                binding.txtTime7.setText(timeFormat.format(selectedTime.time))
////            },
////                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)
////
////            timePicker.show()
////
////        })
////        //setting date format
////        binding.txtstdate.setOnClickListener(View.OnClickListener {
////            val now=Calendar.getInstance()
////            try{
////                var date=dateFormat.parse(binding.txtstdate.text.toString())
////                now.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            val datePicker=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
////                var selectedDate=Calendar.getInstance()
////                selectedDate.set(Calendar.YEAR,year)
////                selectedDate.set(Calendar.MONTH,month)
////                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
////                binding.txtstdate.setText(dateFormat.format(selectedDate.time))
////            },
////            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
////            datePicker.show()
////        })
////        binding.txtenddate.setOnClickListener(View.OnClickListener {
////            val now=Calendar.getInstance()
////            try{
////                var date=dateFormat.parse(binding.txtenddate.text.toString())
////                now.time=date}
////            catch (e:Exception){
////                e.printStackTrace()
////            }
////            val datePicker=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
////                var selectedDate=Calendar.getInstance()
////                selectedDate.set(Calendar.YEAR,year)
////                selectedDate.set(Calendar.MONTH,month)
////                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
////                binding.txtenddate.setText(dateFormat.format(selectedDate.time))
////            },
////                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
////            datePicker.show()
////        })
//    }}
