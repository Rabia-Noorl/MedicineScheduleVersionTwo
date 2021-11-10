
package com.example.medicineschedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.accessibility.AccessibilityViewCommand
import com.example.medicineschedule.databinding.ActivityAddMedicationBinding
import java.text.SimpleDateFormat
import java.util.*

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
        var timeChoice= arrayOf("Once a day","2 times a day","3 times a day","4 times a day","5 times a day","6 times a day","7 times a day")
        var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)
        var dateFormat=SimpleDateFormat("dd MMM YY",Locale.US)
        var  thour:Int
        var tmint:Int
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
            alertDialogIns.setTitle("Select Instruction")
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
        binding.btnReminder.setOnClickListener(View.OnClickListener {
            if(binding.btnReminder.isChecked)
            {binding.linLayoutTime.visibility=View.VISIBLE
            binding.txtvwtime1.visibility=View.VISIBLE
            binding.linLayoutdate.visibility=View.VISIBLE
        }
        else{
            binding.linLayoutTime.visibility=View.GONE
            binding.txtvwtime1.visibility=View.GONE
            binding.linLayoutdate.visibility=View.GONE
                binding.txtvwtime2.visibility = View.GONE
                binding.txtvwtime3.visibility = View.GONE
                binding.txtvwtime4.visibility = View.GONE
                binding.txtvwtime5.visibility=View.GONE
                binding.txtvwtime6.visibility=View.GONE
                binding.txtvwtime7.visibility=View.GONE
        }})
        binding.btnRefill.setOnClickListener(View.OnClickListener {
            if(binding.btnRefill.isChecked)
            {binding.linLayoutsetRefill.visibility=View.VISIBLE
            }
            else{
                binding.linLayoutsetRefill.visibility=View.GONE
            }})
        binding.txtvwtime.setOnClickListener(View.OnClickListener {
            var alertDialogtime=AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(timeChoice,0){dialogInterface:DialogInterface,position:Int->
                binding.txtvwtime.setText(timeChoice[position])

                when(position){
                    0 -> { binding.txtvwtime1.visibility= View.VISIBLE}
                    1 -> {binding.txtvwtime1.visibility= View.VISIBLE
                        binding.txtvwtime2.visibility=View.VISIBLE}
                    2 -> {
                        binding.txtvwtime1.visibility = View.VISIBLE
                        binding.txtvwtime2.visibility=View.VISIBLE
                        binding.txtvwtime3.visibility=View.VISIBLE
                    }
                    3 -> {
                        binding.txtvwtime1.visibility = View.VISIBLE
                        binding.txtvwtime2.visibility=View.VISIBLE
                        binding.txtvwtime3.visibility=View.VISIBLE
                        binding.txtvwtime4.visibility=View.VISIBLE
                    }
                    4->{
                        binding.txtvwtime1.visibility = View.VISIBLE
                        binding.txtvwtime2.visibility=View.VISIBLE
                        binding.txtvwtime3.visibility=View.VISIBLE
                        binding.txtvwtime4.visibility=View.VISIBLE
                        binding.txtvwtime5.visibility=View.VISIBLE

                    }
                    5->{
                        binding.txtvwtime1.visibility =VISIBLE
                        binding.txtvwtime2.visibility=VISIBLE
                        binding.txtvwtime3.visibility=VISIBLE
                        binding.txtvwtime4.visibility=VISIBLE
                        binding.txtvwtime5.visibility=VISIBLE
                        binding.txtvwtime6.visibility= VISIBLE
                    }
                    6->{
                        binding.txtvwtime1.visibility =VISIBLE
                        binding.txtvwtime2.visibility=VISIBLE
                        binding.txtvwtime3.visibility=VISIBLE
                        binding.txtvwtime4.visibility=VISIBLE
                        binding.txtvwtime5.visibility=VISIBLE
                        binding.txtvwtime6.visibility= VISIBLE
                        binding.txtvwtime7.visibility= VISIBLE

                    }
                    else -> {
                        binding.txtvwtime2.visibility = View.GONE
                    binding.txtvwtime3.visibility = View.GONE
                    binding.txtvwtime4.visibility = View.GONE
                    binding.txtvwtime5.visibility=View.GONE
                    binding.txtvwtime6.visibility=View.GONE
                    binding.txtvwtime7.visibility=View.GONE

                    }
                }


                dialogInterface.dismiss()
            }
            alertDialogtime.show()
        })
        binding.txtvwtime1.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
           try{
            var date=timeFormat.parse(binding.txtvwtime1.text.toString())
            calendar.time=date}
           catch (e:Exception){
               e.printStackTrace()
           }
            var timePicker=
                TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime1.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()




        })
        binding.txtvwtime2.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime2.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime2.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwtime3.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime3.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime3.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwtime4.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime4.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime4.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwtime5.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime5.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime5.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwtime6.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime6.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime6.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwtime7.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwtime7.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwtime7.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.stDate.setOnClickListener(View.OnClickListener {
            val now=Calendar.getInstance()
            try{
                var date=dateFormat.parse(binding.stDate.text.toString())
                now.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            val datePicker=
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var selectedDate=Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                binding.stDate.setText(dateFormat.format(selectedDate.time))
            },
            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
        binding.endDate.setOnClickListener(View.OnClickListener {
            val now=Calendar.getInstance()
            try{
                var date=dateFormat.parse(binding.endDate.text.toString())
                now.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            val datePicker=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var selectedDate=Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                binding.endDate.setText(dateFormat.format(selectedDate.time))
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
//        var adapter=ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,doseChoice)
//        binding.dosequantityunit.setAdapter(adapter)
//        binding.dosequantityunit.setThreshold(2)
//        binding.dosequantityunit.setOnClickListener(View.OnClickListener {
//            binding.dosequantityunit.showDropDown()


//        })

    }
}
