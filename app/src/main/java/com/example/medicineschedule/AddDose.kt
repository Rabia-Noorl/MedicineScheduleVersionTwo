
package com.example.medicineschedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddDoseBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.example.medicineschedule.viewModels.MedicineRecViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddDose : AppCompatActivity() {

    lateinit var viewModel: HomeRecViewModel
    lateinit var medViewModel: MedicineRecViewModel



    lateinit var binding: ActivityAddDoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var instructionChoice =
            arrayOf("Before Eating", "After Eating", "While Eating", "Doesn't Matter", "Other")
        var timeChoice= arrayOf("Once a day","2 times a day","3 times a day","4 times a day","5 times a day","6 times a day","7 times a day")
        var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)
        var dateFormat=SimpleDateFormat("dd MMM YY",Locale.US)
        var  thour:Int
        var tmint:Int


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            HomeRecViewModel::class.java)
        medViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            MedicineRecViewModel::class.java)

        binding.doseSave.setOnClickListener {
            addDoseReminder()
        }
        binding.backArrowAddDose.setOnClickListener{
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        binding.Instructions.setOnClickListener(View.OnClickListener {
            var alertDialogIns = AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instruction")
            alertDialogIns.setSingleChoiceItems(
                instructionChoice,
                0
            ) { dialogInterface: DialogInterface, position: Int ->
                binding.Instructions.setText(instructionChoice[position])
                if (position == 4) {
                    binding.customDoseinstruction.visibility = View.VISIBLE
                }
                else{
                    binding.customDoseinstruction.visibility = View.GONE
                }
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogIns.show()
        })
        binding.btndoseReminder.setOnClickListener(View.OnClickListener {
            if(binding.btndoseReminder.isChecked)
            {binding.linLayoutTime.visibility=View.VISIBLE
                binding.txtvwdosetime1.visibility=View.VISIBLE
                binding.linLayoutdosedate.visibility=View.VISIBLE
            }
            else{
                binding.linLayoutTime.visibility=View.GONE
                binding.txtvwdosetime1.visibility=View.GONE
                binding.linLayoutdosedate.visibility=View.GONE
                binding.txtvwdosetime2.visibility = View.GONE
                binding.txtvwdosetime3.visibility = View.GONE
                binding.txtvwdosetime4.visibility = View.GONE
                binding.txtvwdosetime5.visibility=View.GONE
                binding.txtvwdosetime6.visibility=View.GONE
                binding.txtvwdosetime7.visibility=View.GONE
            }})
        binding.btndoseRefill.setOnClickListener(View.OnClickListener {
            if(binding.btndoseRefill.isChecked)
            {binding.linLayoutsetRefill.visibility=View.VISIBLE
            }
            else{
                binding.linLayoutsetRefill.visibility=View.GONE
            }})
        binding.txtvwdosetime.setOnClickListener(View.OnClickListener {
            var alertDialogtime=AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(timeChoice,0){dialogInterface:DialogInterface,position:Int->
                binding.txtvwdosetime.setText(timeChoice[position])

                when(position){
                    0 -> { binding.txtvwdosetime1.visibility= View.VISIBLE}
                    1 -> {binding.txtvwdosetime1.visibility= View.VISIBLE
                        binding.txtvwdosetime2.visibility=View.VISIBLE}
                    2 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility=View.VISIBLE
                        binding.txtvwdosetime3.visibility=View.VISIBLE
                    }
                    3 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility=View.VISIBLE
                        binding.txtvwdosetime3.visibility=View.VISIBLE
                        binding.txtvwdosetime4.visibility=View.VISIBLE
                    }
                    4->{
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility=View.VISIBLE
                        binding.txtvwdosetime3.visibility=View.VISIBLE
                        binding.txtvwdosetime4.visibility=View.VISIBLE
                        binding.txtvwdosetime5.visibility=View.VISIBLE

                    }
                    5->{
                        binding.txtvwdosetime1.visibility =VISIBLE
                        binding.txtvwdosetime2.visibility=VISIBLE
                        binding.txtvwdosetime3.visibility=VISIBLE
                        binding.txtvwdosetime4.visibility=VISIBLE
                        binding.txtvwdosetime5.visibility=VISIBLE
                        binding.txtvwdosetime6.visibility= VISIBLE
                    }
                    6->{
                        binding.txtvwdosetime1.visibility =VISIBLE
                        binding.txtvwdosetime2.visibility=VISIBLE
                        binding.txtvwdosetime3.visibility=VISIBLE
                        binding.txtvwdosetime4.visibility=VISIBLE
                        binding.txtvwdosetime5.visibility=VISIBLE
                        binding.txtvwdosetime6.visibility= VISIBLE
                        binding.txtvwdosetime7.visibility= VISIBLE

                    }
                    else -> {
                        binding.txtvwdosetime2.visibility = View.GONE
                        binding.txtvwdosetime3.visibility = View.GONE
                        binding.txtvwdosetime4.visibility = View.GONE
                        binding.txtvwdosetime5.visibility=View.GONE
                        binding.txtvwdosetime6.visibility=View.GONE
                        binding.txtvwdosetime7.visibility=View.GONE

                    }
                }


                dialogInterface.dismiss()
            }
            alertDialogtime.show()
        })
        binding.txtvwdosetime1.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime1.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime=Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtvwdosetime1.setText(timeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime2.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime2.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime2.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime3.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime3.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime3.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime4.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime4.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime4.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime5.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime5.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime5.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime6.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime6.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime6.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtvwdosetime7.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=timeFormat.parse(binding.txtvwdosetime7.text.toString())
                calendar.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            var timePicker=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime=Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtvwdosetime7.setText(timeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.dosestDate.setOnClickListener(View.OnClickListener {
            val now=Calendar.getInstance()
            try{
                var date=dateFormat.parse(binding.dosestDate.text.toString())
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
                    binding.dosestDate.setText(dateFormat.format(selectedDate.time))
                },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
        binding.doseendDate.setOnClickListener(View.OnClickListener {
            val now=Calendar.getInstance()
            try{
                var date=dateFormat.parse(binding.doseendDate.text.toString())
                now.time=date}
            catch (e:Exception){
                e.printStackTrace()
            }
            val datePicker=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var selectedDate=Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                binding.doseendDate.setText(dateFormat.format(selectedDate.time))
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
    private fun addDoseReminder() {
        var name = binding.medicationName.text.toString()
        var type = binding.medTypeTV.text.toString()
        var quantity = binding.adddosequantity.text.toString()
        var instructions = binding.Instructions.text.toString()
        var time = binding.txtvwdosetime1.text.toString()

        if ( name.length >  0 && type.length >  0 && quantity.length > 0 && time.length > 0 ){

            var remider = ReminderTracker("med", "$name" ,"$time" ,"Take", "$quantity", "Instructions: $instructions", "","","", "${Calendar.getInstance().time}",false)
            viewModel.onAddClick(remider)

            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()

        }else{
            Toast.makeText(this, "Mandatory fields are missing" , Toast.LENGTH_SHORT).show()
        }

    }
}
