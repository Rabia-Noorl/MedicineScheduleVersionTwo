package com.example.medicineschedule.fragments.measurement

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.medicineschedule.AddDose
import com.example.medicineschedule.AddMeasurements
import com.example.medicineschedule.R
import com.example.medicineschedule.classes.AlarmReceiver
import com.example.medicineschedule.classes.updatRVreceiver
import com.example.medicineschedule.classes.updateMeasurement
import com.example.medicineschedule.database.ReminderDatabase
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentMedication2Binding
import com.example.medicineschedule.fragments.appointments.MoreFragment
import com.example.medicineschedule.fragments.medicines.HomeFragment
import com.example.medicineschedule.viewModels.MeasurmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MedicationFragment : Fragment(R.layout.fragment_medication2) {

    lateinit var viewModel: MeasurmentViewModel
    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent
    private lateinit var binding: FragmentMedication2Binding
    lateinit var alertdialogbuilder: AlertDialog.Builder
    var timeFormat= SimpleDateFormat("hh:mm a", Locale.US)

    companion object {
        var statusFlag: Boolean = false
        val recViewListMeasrement = ArrayList<ReminderTracker>()
        lateinit var am: AlarmManager
        lateinit var pi: PendingIntent
        lateinit var amTwo: AlarmManager
        lateinit var pnTwo: PendingIntent

        fun setAlarm( id:Int, context:Context, int:Int) {
            am =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, updateMeasurement::class.java)
            var bundle = Bundle()
            bundle.putSerializable("reminder", recViewListMeasrement as Serializable)
            intent.putExtra("bundle" , bundle)
            // every day at 12 am
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
            calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
            calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +1
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val date = Date()
            if (calendar.time.after(date)) {
                Log.d("elsepart", "from alarm manager")
                Toast.makeText(context,"from alarm manager ${calendar.time.year} ${calendar.time.month} ${calendar[Calendar.DAY_OF_MONTH]} ${calendar.time.hours} ${calendar.time.minutes}", Toast.LENGTH_SHORT).show()
                pi = PendingIntent.getBroadcast(
                    context,
                    800,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                am.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pi)
            }
            else if (calendar.time.before(date)){
                calendar[Calendar.MINUTE] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
                Toast.makeText(context,"from alarm manager ${calendar.time.year} ${calendar.time.month} ${calendar[Calendar.DAY_OF_MONTH]} ${calendar.time.hours} ${calendar.time.minutes}", Toast.LENGTH_SHORT).show()
                pi = PendingIntent.getBroadcast(
                    context,
                    800,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                am.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pi)
            }
        }
        fun setAlarm(list: List<ReminderTracker>, context: Context) {
            lateinit var calendar: Calendar
            list.forEach() {
                var str = it.dateTimes.toString()
                val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                calendar = Calendar.getInstance()
                calendar.time = sdf.parse(str)
                calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
                calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
                calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                calendar[Calendar.HOUR_OF_DAY] = calendar.time.hours
                calendar[Calendar.MINUTE] = calendar.time.minutes
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
                val date = Date()
                if (calendar.time.after(date)) {
                    MedicationFragment.amTwo =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.putExtra("type", "doc")
                    intent.putExtra("name", "You have appointment for ${it.names}!")
                    MedicationFragment.pnTwo = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    MedicationFragment.amTwo.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        MedicationFragment.pnTwo
                    )
                }
                else if (calendar.time.before(date)) {
                    if (it.status == "") {
                        var reminder = ReminderTracker(
                            "${it.reminderType}",
                            "${it.types}",
                            "${it.names}",
                            "${it.dateTimes}",
                            "Taken", "${it.quantity}",
                            "${it.instructions}",
                            "${it.strenght}",
                            "${it.startDate}",
                            "${it.endDate}",
                            "${it.recodeCreationDate}",
                            it.deleteFlage
                        )
                        reminder.id = it.id
                        CoroutineScope(Dispatchers.IO).launch {
                            ReminderDatabase.getDatabase(context).getReminderDao().update(reminder)
                        }
                    }
                    calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
                    MedicationFragment.amTwo =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.putExtra("type", "mes")
                    intent.putExtra("name", "You have measurement reminder for ${it.names}!")
                    Log.d("alarmTime", "${calendar.time.year} ${calendar.time.month} ${calendar[Calendar.DAY_OF_MONTH]} ${calendar.time.hours} ${calendar.time.minutes}")
                    MedicationFragment.pnTwo = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    MedicationFragment.amTwo.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        MedicationFragment.pnTwo
                    )
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedication2Binding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        binding.addMesButton.setOnClickListener{
            val measurementIntent = Intent(getActivity(), AddMeasurements::class.java)
            startActivity(measurementIntent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            MeasurmentViewModel::class.java)

        viewModel.reminder.observe(viewLifecycleOwner) { reminder ->
            dialogeBuild(reminder)
        }

        binding.measurmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.measurmentViewModel= viewModel
            viewModel.allRemiders.observe(viewLifecycleOwner){
                recViewListMeasrement.clear()
                it?.let {
                    var recViewList = ArrayList<ReminderTracker>()
                    it.forEach{
                        if(it.reminderType == "mes" && !it.deleteFlage)
                        {
                            recViewList.add(it)
                            recViewListMeasrement.add(it)
                        }
                    }
                    viewModel.addFun(recViewList)
                    var anim  = binding.measurmentLottieanim
                    var initialText  = binding.initialTV
                    anim.isVisible = recViewList.isEmpty()
                    initialText.isVisible = recViewList.isEmpty()
//                    setAlarm(recViewList)
//                    setAlarm(recViewList, "update")
//                    cancelAlarm(recViewList)
                }
                context?.let { MedicationFragment.setAlarm( 800, it, 0)
                    MedicationFragment.setAlarm(recViewListMeasrement, it)
                }
            }
        }
        return view
    }

    private fun setAlarm(list: List<ReminderTracker>) {
        list.forEach() {
            var str = it.dateTimes.toString()
            val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            calendar = Calendar.getInstance()
            calendar.time = sdf.parse(str)
            calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
            calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
            calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            calendar[Calendar.HOUR_OF_DAY] = calendar.time.hours
            calendar[Calendar.MINUTE] = calendar.time.minutes
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val date = Date()
            if (calendar.time.after(date)) {
                alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                intent.putExtra("type", "mes")
                intent.putExtra("name", "You have measurement reminder for ${it.names}!")
                pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, 0)
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )

            } else if (calendar.time.before(date)) {
                if (it.status == "") {

                    var reminder = ReminderTracker(
                        "${it.reminderType}",
                        "${it.types}",
                        "${it.names}",
                        "${it.dateTimes}",
                        "Taken", "${it.quantity}",
                        "${it.instructions}",
                        "${it.strenght}",
                        "${it.startDate}",
                        "${it.endDate}",
                        "${it.recodeCreationDate}",
                        it.deleteFlage
                    )
                    reminder.id = it.id
                    viewModel.onEditClick(reminder)
                }
            }

        }
    }

    private fun cancelAlarm(list: List<ReminderTracker>) {
        list.forEach() {
            if (it.deleteFlage == true) {
                alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, 0)
                alarmManager.cancel(pendingIntent)
                Toast.makeText(context, "Alarm remove successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun creatNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val name = "ReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("AlarmId", name, importance)
            channel.description = description
            val notificationManager = requireContext().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun dialogeBuild(reminderTracker: ReminderTracker) {
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        var d: Dialog? = context?.let { Dialog(it) }
        d?.setContentView(R.layout.dialog_frag_layout)
        d!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_design);
        d!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        d?.show()

        var unTakeBtn = d?.findViewById<TextView>(R.id.unTakeBtn)
        var takeBtn = d?.findViewById<TextView>(R.id.takeBtn)
        var reschedualBtn = d?.findViewById<TextView>(R.id.reschedualBtn)
        var tyeQuantityV = d?.findViewById<TextView>(R.id.tyeQuantityV)


        var deletBtn = d?.findViewById<ImageView>(R.id.deleteImg)
        var editBtn = d?.findViewById<ImageView>(R.id.editImg)
        var medImg = d?.findViewById<ImageView>(R.id.medImg)

        var nameTv = d?.findViewById<TextView>(R.id.nameTV)
        var statusTV = d?.findViewById<TextView>(R.id.statusTV)

        var sTimeTV = d?.findViewById<TextView>(R.id.sTimeTV)
        var sdayTV = d?.findViewById<TextView>(R.id.sdayTV)
        var strenghtTV = d?.findViewById<TextView>(R.id.strenghtTV)
        var infoTimeTV = d?.findViewById<TextView>(R.id.infoTimeTV)
        var editImg = d?.findViewById<ImageView>(R.id.editImg)

        nameTv.setText(reminderTracker.names)
        sTimeTV.setText(reminderTracker.dateTimes)
        statusTV.setText(reminderTracker.status)
        strenghtTV.setText("${reminderTracker.quantity} ${reminderTracker.types}${reminderTracker.strenght}")
        tyeQuantityV.setText("Measurement at ${reminderTracker.dateTimes}")
        medImg.setImageResource(R.drawable.ic_measurementwhite)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        sdayTV.setText(day)

        unTakeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#f98365"))
            takeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!HomeFragment.statusFlag) {
                unTakeBtn.setText("TAKE")
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    reminderTracker.types,
                    reminderTracker.names,
                    reminderTracker.dateTimes,
                    "",
                    reminderTracker.quantity,
                    reminderTracker.instructions,
                    reminderTracker.strenght,
                    reminderTracker.startDate,
                    reminderTracker.endDate,
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = true
                statusTV.setText("${rem.status}")
                return@setOnClickListener
            } else if (HomeFragment.statusFlag) {
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    "${reminderTracker.types}",
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Taken",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                statusTV.setTextColor(getResources().getColor(R.color.doneColor, null))
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = false
                statusTV.setText("${rem.status}")
                unTakeBtn.setText("UN_TAKE")
                return@setOnClickListener
            }
        }

        takeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            takeBtn.setTextColor(Color.parseColor("#f98365"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!HomeFragment.statusFlag) {
                val rem = ReminderTracker(
                    reminderTracker.reminderType,
                    reminderTracker.types,
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Missed",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = true
                statusTV.setText("${rem.status}")
                takeBtn.setText("SKIPPED")
                statusTV.setTextColor(getResources().getColor(R.color.errorColor, null))
                return@setOnClickListener
            } else if (HomeFragment.statusFlag) {
                var rem = ReminderTracker(
                    reminderTracker.reminderType,
                    "${reminderTracker.types}",
                    "${reminderTracker.names}",
                    "${reminderTracker.dateTimes}",
                    "Skipped",
                    "${reminderTracker.quantity}",
                    "${reminderTracker.instructions}",
                    "${reminderTracker.strenght}",
                    "${reminderTracker.startDate}",
                    "${reminderTracker.endDate}",
                    reminderTracker.recodeCreationDate,
                    reminderTracker.deleteFlage
                )
                rem.id = reminderTracker.id
                viewModel.onEditClick(rem)
                HomeFragment.statusFlag = false
                statusTV.setText("${rem.status}")
                takeBtn.setText("MISSED")
                statusTV.setTextColor(getResources().getColor(R.color.greyColr, null))
                return@setOnClickListener
            }
        }
        reschedualBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            takeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            reschedualBtn.setTextColor(Color.parseColor("#f98365"))

            var calendar = Calendar.getInstance()
            try {
                var date = timeFormat.parse(reminderTracker.dateTimes.toString())
                calendar.time = date
            } catch (e: Exception) {
                e.printStackTrace()
            }
            var timePicker =
                TimePickerDialog(
                    context, { view, hourOfDay, minute ->
                        var selectedTime = Calendar.getInstance()
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedTime.set(Calendar.MINUTE, minute)
                        var time = timeFormat.format(selectedTime.time)
                        var rem = ReminderTracker(
                            reminderTracker.reminderType,
                            reminderTracker.types,
                            reminderTracker.names,
                            time,
                            "",
                            reminderTracker.quantity,
                            reminderTracker.instructions,
                            reminderTracker.strenght,
                            reminderTracker.startDate,
                            reminderTracker.endDate,
                            reminderTracker.recodeCreationDate,
                            reminderTracker.deleteFlage
                        )
                        rem.id = reminderTracker.id
                        viewModel.onEditClick(rem)
                        sTimeTV.setText(time)
                        tyeQuantityV.setText("at $time")
                    },
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
                )
            timePicker.show()

        }

        deletBtn.setOnClickListener {
            alertdialogbuilder = AlertDialog.Builder(requireActivity())
            alertdialogbuilder.setCancelable(false)
            alertdialogbuilder.setTitle("Delete").setIcon(R.drawable.ic_delete)
                .setMessage("Are you sure you want to Delete it?").setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->
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
                    viewModel.deletDrug(rem)
                    d?.cancel()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()
            editBtn.setOnClickListener {
                val medicineIntent = Intent(getActivity(), AddDose::class.java)
                startActivity(medicineIntent)
            }
        }
        editImg.setOnClickListener {
            val intent = Intent(context, AddMeasurements::class.java)
            intent.putExtra("mes", reminderTracker)
            startActivity(intent)
        }
    }

    private fun setAlarm(list: List<ReminderTracker>, update:String) {
//        val c = Calendar.getInstance()
//        c[Calendar.HOUR_OF_DAY] = 1 //then set the other fields to 0
//        c[Calendar.MINUTE] = 0
//        c[Calendar.SECOND] = 0
//        c[Calendar.MILLISECOND] = 0
//        c.timeInMillis - System.currentTimeMillis()
//        Log.d("timefromfragmnet", "$c")

        val calendar = Calendar.getInstance()
        calendar[
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH] ,
                calendar[Calendar.DAY_OF_MONTH] + 1,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE]
        ] = 0
        alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, updatRVreceiver::class.java)
        var bundle = Bundle()
        bundle.putSerializable("reminder", list as Serializable)
        intent.putExtra("bundle" , bundle)
        pendingIntent = PendingIntent.getBroadcast(context, 987654321, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,AlarmManager.INTERVAL_DAY,
            pendingIntent)
    }
}