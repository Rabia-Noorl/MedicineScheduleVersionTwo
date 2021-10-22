package com.example.medicineschedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.medicineschedule.databinding.ActivityAddDoseBinding
import com.example.medicineschedule.databinding.ActivityAddMedicationBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddDose : AppCompatActivity() {
    lateinit var binding: ActivityAddDoseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddDoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var instructionChoices= arrayOf("Before Eating","After Eating","While Eating","Doesn't Matter")
        var timeChoices= arrayOf("Once a day","2 times a day","3 times a day","4 times a day","5 times a day","6 times a day","7 times a day")
        var  dthour:Int
        var dtmint:Int
        var dosetimeFormat= SimpleDateFormat("hh:mm a", Locale.US)
        var dosedateFormat= SimpleDateFormat("dd MMM YY", Locale.US)

        binding.txtvwdoseInstructions.setOnClickListener(View.OnClickListener {

            var alertDialogIns= AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instructions")
            alertDialogIns.setSingleChoiceItems(instructionChoices,0){ dialogInterface: DialogInterface, position:Int->
                binding.txtvwdoseInstructions.setText(instructionChoices[position])
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogIns.show()
        })

        binding.txtvwdoseTime.setOnClickListener(View.OnClickListener {

            var alertDialogtime= AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(timeChoices,0){ dialogInterface: DialogInterface, position:Int->
                binding.txtvwdoseTime.setText(timeChoices[position])
//                binding.scrollView.visibility=View.VISIBLE
//                binding.linearlayout2.visibility=View.VISIBLE
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogtime.show()
        })
        binding.switchdoseReminder.setOnClickListener(View.OnClickListener {
            if(binding.switchdoseReminder.isChecked)
            {
                binding.txtipdoseTime.visibility= View.VISIBLE
                binding.scrollViewdose.visibility= View.VISIBLE
                binding.linearlayout2.visibility= View.VISIBLE
//                binding.txtstdate.visibility=View.VISIBLE
//                binding.txtenddate.visibility=View.VISIBLE
            }
            else{
                binding.txtipdoseTime.visibility= View.GONE
                binding.scrollViewdose.visibility= View.GONE
                binding.linearlayout2.visibility= View.GONE

//                binding.txtstdate.visibility=View.GONE
//                binding.txtenddate.visibility=View.GONE


            }
        })
        //Setting time format
        binding.txtdoseTime1.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime1.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime1.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime2.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime2.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime2.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime3.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime3.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime3.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime4.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime4.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime4.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime5.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime5.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime5.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime6.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime6.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime6.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtdoseTime7.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=dosetimeFormat.parse(binding.txtdoseTime7.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                var selectedTime= Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                binding.txtdoseTime7.setText(dosetimeFormat.format(selectedTime.time))
            },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        //setting date format
        binding.txtdosestdate.setOnClickListener(View.OnClickListener {
            val now= Calendar.getInstance()
            try{
                var date=dosedateFormat.parse(binding.txtdosestdate.text.toString())
                now.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            val datePicker=
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var selectedDate= Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                binding.txtdosestdate.setText(dosedateFormat.format(selectedDate.time))
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
        binding.txtdoseenddate.setOnClickListener(View.OnClickListener {
            val now= Calendar.getInstance()
            try{
                var date=dosedateFormat.parse(binding.txtdoseenddate.text.toString())
                now.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            val datePicker=
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                var selectedDate= Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                binding.txtdoseenddate.setText(dosedateFormat.format(selectedDate.time))
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
    }}
