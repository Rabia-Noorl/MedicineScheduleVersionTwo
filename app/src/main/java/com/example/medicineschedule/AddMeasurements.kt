package com.example.medicineschedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.medicineschedule.databinding.ActivityAddMeasurementsBinding
import com.example.medicineschedule.databinding.ActivityAddMedicationBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class AddMeasurements : AppCompatActivity() {
    lateinit var binding: ActivityAddMeasurementsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddMeasurementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var measurementsChoice= arrayOf("Blood Pressure", "Blood Sugar", "Weight","Temperature","Heart Rate", "Other")
        var  dthour:Int
        var dtmint:Int
        var measuretimeFormat= SimpleDateFormat("hh:mm a", Locale.US)
        var measuredateFormat= SimpleDateFormat("dd MMM YY", Locale.US)
        binding.txtvwMeasurements.setOnClickListener(View.OnClickListener {

            var alertDialogIns= AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instructions")
            alertDialogIns.setSingleChoiceItems(measurementsChoice,0){ dialogInterface: DialogInterface, position:Int->
                binding.txtvwMeasurements.setText(measurementsChoice[position])
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogIns.show()
        })
        binding.txtvwMeasurementTime.setOnClickListener(View.OnClickListener {

            var alertDialogtime= AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(measurementsChoice,0){ dialogInterface: DialogInterface, position:Int->
                binding.txtvwMeasurementTime.setText(measurementsChoice[position])
//                binding.scrollView.visibility=View.VISIBLE
//                binding.linearlayout2.visibility=View.VISIBLE
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogtime.show()
        })
        binding.switchMeasurementReminder.setOnClickListener(View.OnClickListener {
            if(binding.switchMeasurementReminder.isChecked)
            {
                binding.txtipMeasurementTime.visibility= View.VISIBLE
                binding.scrollViewMeasurement.visibility= View.VISIBLE
                binding.linearlayout2.visibility= View.VISIBLE
//                binding.txtstdate.visibility=View.VISIBLE
//                binding.txtenddate.visibility=View.VISIBLE
            }
            else{
                binding.txtipMeasurementTime.visibility= View.GONE
                binding.scrollViewMeasurement.visibility= View.GONE
                binding.linearlayout2.visibility= View.GONE

//                binding.txtstdate.visibility=View.GONE
//                binding.txtenddate.visibility=View.GONE


            }
        })
        //Setting time format
        binding.txtMeasurementTime1.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime1.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime1.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime2.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime2.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime2.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime3.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime3.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime3.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime4.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime4.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime4.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime5.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime5.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime5.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime6.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime6.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime6.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        binding.txtMeasurementTime7.setOnClickListener(View.OnClickListener {
            var calendar= Calendar.getInstance()
            try{
                var date=measuretimeFormat.parse(binding.txtMeasurementTime7.text.toString())
                calendar.time=date}
            catch (e: Exception){
                e.printStackTrace()
            }
            var timePicker=
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    var selectedTime= Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    binding.txtMeasurementTime7.setText(measuretimeFormat.format(selectedTime.time))
                },
                    calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false)

            timePicker.show()

        })
        //setting date format
        binding.txtMeasurementstdate.setOnClickListener(View.OnClickListener {
            val now= Calendar.getInstance()
            try{
                var date=measuredateFormat.parse(binding.txtMeasurementstdate.text.toString())
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
                    binding.txtMeasurementstdate.setText(measuredateFormat.format(selectedDate.time))
                },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
        binding.txtMeasurementenddate.setOnClickListener(View.OnClickListener {
            val now= Calendar.getInstance()
            try{
                var date=measuredateFormat.parse(binding.txtMeasurementenddate.text.toString())
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
                    binding.txtMeasurementenddate.setText(measuredateFormat.format(selectedDate.time))
                },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        })
    }}
