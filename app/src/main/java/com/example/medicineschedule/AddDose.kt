package com.example.medicineschedule

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.ActivityAddDoseBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.example.medicineschedule.viewModels.MedicineRecViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_doctor.*
import kotlinx.android.synthetic.main.activity_add_dose.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import com.example.medicineschedule.classes.AlarmReceiver
import java.text.ParseException


class AddDose : AppCompatActivity() {
    lateinit var viewModel: HomeRecViewModel
    lateinit var medViewModel: MedicineRecViewModel
    lateinit var binding: ActivityAddDoseBinding
    lateinit var alertdialogbuilder: AlertDialog.Builder

    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent

    var selectedIndex = 0

    val db = FirebaseFirestore.getInstance()
    val brandsHints: MutableList<String> = mutableListOf()

    companion object {
        val timeList = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent()
        val record: ReminderTracker? = intent.getSerializableExtra("med") as ReminderTracker?
        binding.medicationName.setText(record?.names)
        binding.Instructions.setText(record?.instructions)
        binding.medTypeTV.setText(record?.types)
        val parts = record?.quantity?.split(" ")
        val firstPart = parts?.get(0)
        binding.adddosequantity.setText(firstPart.toString())
        binding.customDoseinstruction.setText(record?.instructions)
        binding.txtvwdosetime1.setText(record?.dateTimes)


        db.collection("Medicines").get().addOnSuccessListener {
            it.forEach {
                brandsHints.add(it.id)
            }
            val hintAdaper =
                this?.let {
                    ArrayAdapter<String>(
                        it,
                        R.layout.custom_list_item,
                        R.id.text_view_list_item,
                        brandsHints
                    )
                }
            binding.medicationName.setAdapter(hintAdaper)
        }

        binding.medicationName.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Display the clicked item using toast
                DictionaryActivity.drugName = selectedItem
            }


        var instructionChoice =
            arrayOf("Before Eating", "After Eating", "While Eating", "Doesn't Matter", "Other")
        var timeChoice = arrayOf(
            "Once a day",
            "2 times a day",
            "3 times a day",
            "4 times a day",
            "5 times a day",
            "6 times a day",
            "7 times a day"
        )
        var tabChoice = arrayOf(
            "Tablet",
            "Capsule",
            "Syrup",
            "Injection",
            "Powder",
            "Drops",
            "Mouthwash",
            "Inhaler",
            "Spray",
            "Cream",
            "Ointment",
            "Gel",
            "Lotion",
            "Physiotherapy",
            "Treatment Session",
            "Other"
        )
        arrayOf("Before Eating", "After Eating", "While Eating", "Doesn't Matter", "Other")
        var selectedDoseType = tabChoice[selectedIndex]
        binding.medTypeTV.setText(selectedDoseType)
        val uri = "@drawable/pill"
        val res = resources.getIdentifier(uri, "drawable", this.packageName)
        binding.doseimgView.setImageResource(res)
        binding.cardViewdose.visibility = View.VISIBLE
        var selectedIns = instructionChoice[selectedIndex]
        binding.Instructions.setText(selectedIns)
        var selectedTime = timeChoice[selectedIndex]
        binding.txtvwdosetime.setText(selectedTime)
        binding.txtvwdosetime1.visibility = VISIBLE
        var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        var dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(
            HomeRecViewModel::class.java
        )
        medViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(
            MedicineRecViewModel::class.java
        )
        //spinners
        //set spinners
        var unitsChoice = resources.getStringArray(R.array.unitOptions)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        var unitsChoice1 = resources.getStringArray(R.array.unitOptions1)
        val adapter1 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice2 = resources.getStringArray(R.array.unitOptions2)
        val adapter2 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice3 = resources.getStringArray(R.array.unitOptions3)
        val adapter3 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice3)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice4 = resources.getStringArray(R.array.unitOptions4)
        val adapter4 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice4)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice5 = resources.getStringArray(R.array.unitOptions5)
        val adapter5 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice5)
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice6 = resources.getStringArray(R.array.unitOptions6)
        val adapter6 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice6)
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice7 = resources.getStringArray(R.array.unitOptions7)
        val adapter7 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice7)
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var unitsChoice8 = resources.getStringArray(R.array.unitOptions8)
        val adapter8 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, unitsChoice8)
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //set tab choices
        binding.medTypeTV.setOnClickListener(View.OnClickListener {
            var alertDialogmedType = AlertDialog.Builder(this)
            alertDialogmedType.setTitle("Select Medication Type")
            alertDialogmedType.setSingleChoiceItems(
                tabChoice,
                selectedIndex
            ) { dialogInterface: DialogInterface, position: Int ->
                selectedIndex = position
                binding.medTypeTV.setText(tabChoice[position])
                if (position == 0) {
                    val uri = "@drawable/pill"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 1) {
                    binding.spinner.adapter = adapter1
                    val uri = "@drawable/capsule"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 2) {
                    val uri = "@drawable/syrup"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter2
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 3) {
                    val uri = "@drawable/syringe"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter3
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 4) {
                    val uri = "@drawable/powder"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter4
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 5) {
                    val uri = "@drawable/drops"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter5
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 6) {
                    val uri = "@drawable/mouthwash"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 7) {
                    val uri = "@drawable/inhaler"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter7
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 8) {
                    val uri = "@drawable/spray"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter8
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 9) {
                    val uri = "@drawable/cream"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 10) {
                    val uri = "@drawable/ointment"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 11) {
                    val uri = "@drawable/gel"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 12) {
                    val uri = "@drawable/lotion"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                if (position == 13) {
                    val uri = "@drawable/physiotherapy"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.cardViewdose.visibility = View.GONE
                }
                if (position == 14) {
                    val uri = "@drawable/treatment"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.cardViewdose.visibility = View.GONE

                }
                if (position == 15) {
                    val uri = "@drawable/others"
                    val res = resources.getIdentifier(uri, "drawable", this.packageName)
                    binding.doseimgView.setImageResource(res)
                    binding.spinner.adapter = adapter6
                    binding.cardViewdose.visibility = View.VISIBLE
                }
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogmedType.show()
        })

        binding.doseSave.setOnClickListener {
            if (record == null) {
                addDoseReminder()
            } else {
                var Name = binding.medicationName.text.toString()
                var time = binding.txtvwdosetime1.text
                if (Name.isNotEmpty() && time.isNotEmpty()) {
                    var reminder = ReminderTracker(
                        "med",
                        "${record.types}",
                        "$Name",
                        "$time",
                        "${record.status}",
                        "${record.quantity}",
                        "${record.instructions}",
                        "${record.strenght}",
                        "${record.startDate}",
                        "${record.endDate}",
                        "${record.recodeCreationDate}",
                        record.deleteFlage
                    )
                    reminder.id = record.id
                    viewModel.onEditClick(reminder)
                    val intent = Intent(this, HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                }
            }
        }
        binding.backArrowAddDose.setOnClickListener {
            alertdialogbuilder = AlertDialog.Builder(this)
            alertdialogbuilder.setCancelable(false)
            alertdialogbuilder.setTitle("Confirm Exit!!!").setIcon(R.drawable.exitapp)
                .setMessage("Are you sure you want to Exit?").setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
                    this.finish()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()

        }

        binding.Instructions.setOnClickListener(View.OnClickListener {
            var alertDialogIns = AlertDialog.Builder(this)
            alertDialogIns.setTitle("Select Instruction")
            alertDialogIns.setSingleChoiceItems(
                instructionChoice, selectedIndex
            ) { dialogInterface: DialogInterface, position: Int ->
                selectedIndex = position
                binding.Instructions.setText(instructionChoice[position])
                if (position == 4) {
                    binding.customDoseinstruction.visibility = View.VISIBLE
                } else {
                    binding.customDoseinstruction.visibility = View.GONE
                }
                dialogInterface.dismiss()
//                Toast.makeText(applicationContext,"You Selected:"+doseChoice[position],Toast.LENGTH_SHORT).show()
            }
            alertDialogIns.show()
        })
        binding.txtvwdosetime.setOnClickListener(View.OnClickListener {
            var alertDialogtime = AlertDialog.Builder(this)
            alertDialogtime.setTitle("How many times per day?")
            alertDialogtime.setSingleChoiceItems(
                timeChoice,
                selectedIndex
            ) { dialogInterface: DialogInterface, position: Int ->
                selectedIndex = position
                binding.txtvwdosetime.setText(timeChoice[position])

                when (position) {
                    0 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility = View.GONE
                        binding.txtvwdosetime3.visibility = View.GONE
                        binding.txtvwdosetime4.visibility = View.GONE
                        binding.txtvwdosetime5.visibility = View.GONE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE
                    }
                    1 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility = View.VISIBLE
                        binding.txtvwdosetime3.visibility = View.GONE
                        binding.txtvwdosetime4.visibility = View.GONE
                        binding.txtvwdosetime5.visibility = View.GONE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE

                    }
                    2 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility = View.VISIBLE
                        binding.txtvwdosetime3.visibility = View.VISIBLE
                        binding.txtvwdosetime4.visibility = View.GONE
                        binding.txtvwdosetime5.visibility = View.GONE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE
                    }
                    3 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility = View.VISIBLE
                        binding.txtvwdosetime3.visibility = View.VISIBLE
                        binding.txtvwdosetime4.visibility = View.VISIBLE
                        binding.txtvwdosetime5.visibility = View.GONE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE
                    }
                    4 -> {
                        binding.txtvwdosetime1.visibility = View.VISIBLE
                        binding.txtvwdosetime2.visibility = View.VISIBLE
                        binding.txtvwdosetime3.visibility = View.VISIBLE
                        binding.txtvwdosetime4.visibility = View.VISIBLE
                        binding.txtvwdosetime5.visibility = View.VISIBLE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE
                    }
                    5 -> {
                        binding.txtvwdosetime1.visibility = VISIBLE
                        binding.txtvwdosetime2.visibility = VISIBLE
                        binding.txtvwdosetime3.visibility = VISIBLE
                        binding.txtvwdosetime4.visibility = VISIBLE
                        binding.txtvwdosetime5.visibility = VISIBLE
                        binding.txtvwdosetime6.visibility = VISIBLE
                        binding.txtvwdosetime7.visibility = View.GONE

                    }
                    6 -> {
                        binding.txtvwdosetime1.visibility = VISIBLE
                        binding.txtvwdosetime2.visibility = VISIBLE
                        binding.txtvwdosetime3.visibility = VISIBLE
                        binding.txtvwdosetime4.visibility = VISIBLE
                        binding.txtvwdosetime5.visibility = VISIBLE
                        binding.txtvwdosetime6.visibility = VISIBLE
                        binding.txtvwdosetime7.visibility = VISIBLE

                    }
                    else -> {
                        binding.txtvwdosetime2.visibility = View.GONE
                        binding.txtvwdosetime3.visibility = View.GONE
                        binding.txtvwdosetime4.visibility = View.GONE
                        binding.txtvwdosetime5.visibility = View.GONE
                        binding.txtvwdosetime6.visibility = View.GONE
                        binding.txtvwdosetime7.visibility = View.GONE
                    }
                }


                dialogInterface.dismiss()
            }
            alertDialogtime.show()
        })
        binding.txtvwdosetime1.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime1.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker =
                TimePickerDialog(
                    this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        var selectedTime = Calendar.getInstance()
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedTime.set(Calendar.MINUTE, minute)
                        binding.txtvwdosetime1.setText(timeFormat.format(selectedTime.time))
                    },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                )

            timePicker.show()

        })
        binding.txtvwdosetime2.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime2.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime2.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
        binding.txtvwdosetime3.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime3.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime3.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
        binding.txtvwdosetime4.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime4.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime4.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
        binding.txtvwdosetime5.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime5.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime5.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
        binding.txtvwdosetime6.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime6.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime6.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
        binding.txtvwdosetime7.setOnClickListener(View.OnClickListener {
            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(binding.txtvwdosetime7.text.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker = TimePickerDialog(
                this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    binding.txtvwdosetime7.setText(timeFormat.format(selectedTime.time))
                },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            )

            timePicker.show()

        })
    }

    override fun onBackPressed() {
        alertdialogbuilder = AlertDialog.Builder(this)
        alertdialogbuilder.setCancelable(false)
        alertdialogbuilder.setTitle("Confirm Exit!!!").setIcon(R.drawable.exitapp)
            .setMessage("Are you sure you want to Exit?").setCancelable(true)
            .setPositiveButton("Yes") { dialogInterface, it ->
                this.finish()
            }
            .setNegativeButton("No") { dialogInterface, it ->
                dialogInterface.cancel()


            }.show()
    }

    private fun addDoseReminder() {
        val sdf = SimpleDateFormat("EE MMM dd yyyy 'at:' hh:mm a ")
        val currentDate = sdf.format(Date())
        timeList.clear()
        var time = binding.txtvwdosetime1.text.toString()
        var time1 = binding.txtvwdosetime2.text.toString()
        var time2 = binding.txtvwdosetime3.text.toString()
        var time3 = binding.txtvwdosetime4.text.toString()
        var time4 = binding.txtvwdosetime5.text.toString()
        var time5 = binding.txtvwdosetime6.text.toString()
        var time6 = binding.txtvwdosetime7.text.toString()

        timeList.add(time)
        timeList.add(time1)
        timeList.add(time2)
        timeList.add(time3)
        timeList.add(time4)
        timeList.add(time5)
        timeList.add(time6)

        var name = binding.medicationName.text.toString()
        var type = binding.medTypeTV.text.toString()
        var quantity = binding.adddosequantity.text.toString()
        var instructions = binding.Instructions.text.toString()
        var measurmentUnits = binding.spinner.selectedItem.toString()
        timeList.forEach {
            if (name.length > 0 && type.length > 0 && quantity.length > 0 && it.length > 0) {
                var remider = ReminderTracker(
                    "med",
                    " $type",
                    "$name",
                    "$it",
                    "",
                    "$quantity $measurmentUnits",
                    "$instructions",
                    "",
                    "$currentDate",
                    "",
                    "$currentDate",
                    false
                )
               // setAlarm(remider)
                viewModel.onAddClick(remider)
                Toast.makeText(this,"${remider.id} ${remider.dateTimes}", Toast.LENGTH_LONG).show()
            } else {
            }
        }
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()

    }

   // private fun setAlarm(reminderTracker: ReminderTracker) {
//        var str = reminderTracker.dateTimes.toString()
//        val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
//        calendar = Calendar.getInstance()
//        calendar.time = sdf.parse(str)
//        calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
//        calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
//        calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
//        calendar[Calendar.HOUR_OF_DAY] = calendar.time.hours
//        calendar[Calendar.MINUTE] = calendar.time.minutes
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//        val date = Date()
//        if (calendar.time.after(date)) {
//            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(this, AlarmReceiver::class.java)
//            intent.putExtra("type", "med")
//            intent.putExtra(
//                "name",
//                "You have ${reminderTracker.names} ${reminderTracker.types} to take!"
//            )
//            pendingIntent = PendingIntent.getBroadcast(this, reminderTracker.id, intent, 0)
//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                pendingIntent
//            )
//        }
//    }
}
