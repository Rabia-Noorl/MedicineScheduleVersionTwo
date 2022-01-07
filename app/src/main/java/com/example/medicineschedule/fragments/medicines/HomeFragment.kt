package com.example.medicineschedule.fragments.medicines

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_frag_layout.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

import android.app.AlarmManager

import android.app.PendingIntent
import android.content.ContentValues.TAG
import androidx.work.OneTimeWorkRequest
import com.example.medicineschedule.classes.*
import com.example.medicineschedule.database.ReminderDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    lateinit var viewModel: HomeRecViewModel
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    lateinit var alertdialogbuilder: AlertDialog.Builder

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    companion object {
        var statusFlag: Boolean = false
        val recViewList = ArrayList<ReminderTracker>()
        lateinit var am: AlarmManager
        lateinit var pi: PendingIntent
        lateinit var amTwo: AlarmManager
        lateinit var pnTwo: PendingIntent

        fun setAlarm( id:Int, context:Context, int:Int) {
            am =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, updatRVreceiver::class.java)
            var bundle = Bundle()
            bundle.putSerializable("reminder", recViewList as Serializable)
            intent.putExtra("bundle" , bundle)
            // every day at 9 am
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = Calendar.getInstance().get(Calendar.YEAR)
            calendar[Calendar.MONTH] = Calendar.getInstance().get(Calendar.MONTH)
            calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
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
                    600,
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
                Toast.makeText(context,"from else ${calendar.time.year} ${calendar.time.month} ${calendar[Calendar.DAY_OF_MONTH]} ${calendar.time.hours} ${calendar.time.minutes}", Toast.LENGTH_SHORT).show()
                pi = PendingIntent.getBroadcast(
                    context,
                    600,
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
                    amTwo =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.putExtra("type", "med")
                    intent.putExtra("name", "You have ${it.names} ${it.types} to take!")
                    pnTwo = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    amTwo.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pnTwo
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
                            ReminderDatabase.getDatabase(context).getReminderDao().insert(reminder)
                        }
                    }
                    calendar[Calendar.DAY_OF_MONTH] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
                    amTwo =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.putExtra("type", "med")
                    intent.putExtra("name", "You have ${it.names} ${it.types} to take!")
                    Log.d("alarmTime", "${calendar.time.year} ${calendar.time.month} ${calendar[Calendar.DAY_OF_MONTH]} ${calendar.time.hours} ${calendar.time.minutes}")
                    pnTwo = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    amTwo.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pnTwo
                    )
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { setUpWorker(it) }
        // setUpWorker()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        creatNotificationChannel()
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser!!
        val parts = currentUser.displayName?.split(" ")
        val firstName = parts?.get(0)
        binding.userNametextView.setText(firstName)
        Glide.with(this).load(currentUser?.photoUrl).into(binding.imageProfile)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(
            HomeRecViewModel::class.java
        )

        binding.viewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
        viewModel.reminder.observe(viewLifecycleOwner) { reminder ->
            dialogeBuild(reminder)
        }
        viewModel.allRemiders.observe(viewLifecycleOwner) {
            recViewList.clear()
            it?.let {
                val recViewListTwo = ArrayList<ReminderTracker>()
                it.forEach {
                    if (it.reminderType == "med" && !it.deleteFlage) {
                        recViewListTwo.add(it)
                        recViewList.add(it)
                    }
                }
                viewModel.addFun(recViewListTwo)
                val anim = binding.homeLottieanim
                val getStarted = binding.textView9
                val initialText = binding.initialTV
                anim.isVisible = recViewListTwo.isEmpty()
                getStarted.isVisible = recViewListTwo.isEmpty()
                initialText.isVisible = recViewListTwo.isEmpty()
            }
            context?.let { HomeFragment.setAlarm( 600, it, 0)
                setAlarm(recViewList, it)}

        }

        binding.searchView2.setOnClickListener {
            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)
        }

//        binding.floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(object :
//            FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
//            override fun onMenuExpanded() {
//                binding.blureLayout.isVisible = true
//            }

//            override fun onMenuCollapsed() {
//                binding.blureLayout.isVisible = false
//            }
//        })

    }

    private fun uiViews() {

        binding.addMedicine.setOnClickListener {
            onClick(it)
        }
//        binding.addMeasurement.setOnClickListener{
//            onClick(it)
//        }
////        binding.addReminder.setOnClickListener{
////            onClick(it)
////        }
//        binding.addDoctor.setOnClickListener{
//            onClick(it)
//        }

    }

    fun onClick(v: View?) {
        when (v?.id) {

            R.id.addMedicine -> {
                val doseIntent = Intent(getActivity(), AddDose::class.java)
                startActivity(doseIntent)
            }
        }
    }

    private fun checkUser() {
        //get current user
        val firebaseUser = mAuth.currentUser
        if (firebaseUser == null) {
            //user not logged in
            startActivity(Intent(getActivity(), SignIn::class.java))
        } else {
            //user logged in.. Get user Info
            val email = firebaseUser.email
            val userName = firebaseUser.displayName
            val profilePhoto = firebaseUser.photoUrl
            //  set Email
            binding.userNametextView.text=email
            binding.userNametextView.text = userName
            binding.imageProfile.setImageURI(profilePhoto)
        }

    }


//    fun cancelAlarm(id: Int) {
//            alarmManager =
//                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(context, AlarmReceiver::class.java)
//            pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            alarmManager.cancel(pendingIntent)
//    }

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
        var cancelBtn = d?.findViewById<ImageView>(R.id.cancelBtn)

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
        tyeQuantityV.setText("at ${reminderTracker.dateTimes} ")

        val calendar = Calendar.getInstance()
        val date = calendar.time
        val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        sdayTV.setText(day)

        if (reminderTracker.reminderType.toString() == "med") {
            // strenghtTV.setText("${reminderTracker.quantity} ${reminderTracker.types}${reminderTracker.strenght}")
            //tyeQuantityV.setText("at ${reminderTracker.dateTimes} ")
        } else if (reminderTracker.reminderType == "mes") {
            tyeQuantityV.setText("Measurement at ${reminderTracker.dateTimes}")
        } else if (reminderTracker.reminderType == "doc") {
            tyeQuantityV.setText("Apointment at ${reminderTracker.dateTimes} ${reminderTracker.status} ")
        }

        unTakeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#f98365"))
            takeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!statusFlag) {
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
                statusFlag = true
                statusTV.setText("${rem.status}")
                return@setOnClickListener
            } else if (statusFlag) {
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
                statusFlag = false
                statusTV.setText("${rem.status}")
                unTakeBtn.setText("UN_TAKE")
                return@setOnClickListener
            }
        }

        takeBtn.setOnClickListener {
            unTakeBtn.setTextColor(Color.parseColor("#FFFFFF"))
            takeBtn.setTextColor(Color.parseColor("#f98365"))
            reschedualBtn.setTextColor(Color.parseColor("#FFFFFF"))
            if (!statusFlag) {
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
                statusFlag = true
                statusTV.setText("${rem.status}")
                takeBtn.setText("SKIPPED")
                statusTV.setTextColor(getResources().getColor(R.color.errorColor, null))
                return@setOnClickListener
            } else if (statusFlag) {
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
                statusFlag = false
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
                    cancelAlarm(rem)
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
            d?.cancel()
            val intent = Intent(context, AddDose::class.java)
            intent.putExtra("med", reminderTracker)
            startActivity(intent)
        }
        cancelBtn.setOnClickListener{
            d?.cancel()
        }

    }

    fun setAlarm(list: List<ReminderTracker>, id:Int) {
        alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, updatRVreceiver::class.java)
        var bundle = Bundle()
        bundle.putSerializable("reminder", list as Serializable)
        intent.putExtra("bundle" , bundle)
        // every day at 9 am
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        // if it's after or equal 9 am schedule for next day
        if (Calendar.getInstance()[Calendar.HOUR_OF_DAY] >= 23) {
            Log.e(TAG, "Alarm will schedule for next day!")
            calendar.add(Calendar.DAY_OF_YEAR, 1) // add, not set!
        } else {
            Log.e(TAG, "Alarm will schedule for today!")
        }
        calendar[Calendar.HOUR_OF_DAY] = 22
        calendar[Calendar.MINUTE] = 12
        calendar[Calendar.SECOND] = 0
        pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent)
    }

    fun Set12nnAlarm(context: Context, list: List<ReminderTracker>) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, updatRVreceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        var bundle = Bundle()
        bundle.putSerializable("reminder", list as Serializable)
        intent.putExtra("bundle" , bundle)

        // every day at 9 am
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        // if it's after or equal 9 am schedule for next day
        if (Calendar.getInstance()[Calendar.HOUR_OF_DAY] >= 20) {
            Toast.makeText(context, "Alarm will schedule for next day from homeFrag", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Alarm will schedule for next day!")
            calendar.add(Calendar.DAY_OF_YEAR, 1) // add, not set!
        } else {
            Toast.makeText(context, "Alarm will schedule for today!", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Alarm will schedule for today!")
        }
        calendar[Calendar.HOUR_OF_DAY] = 14
        calendar[Calendar.MINUTE] = 18
        calendar[Calendar.SECOND] = 0
        am.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, Calendar.getInstance().getTimeInMillis()+(24*60*60*1000), pi
        )
    }
    private fun setUpWorker(context: Context) {
        val workRequest =
            OneTimeWorkRequest.Builder(WorkerUpdateRV::class.java).build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
    private fun cancelAlarm(list: List<ReminderTracker>) {
        list.forEach() {
            if (it.deleteFlage == true) {
                Companion.amTwo =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, AlarmReceiver::class.java)
                Companion.pnTwo = PendingIntent.getBroadcast(context, it.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                Companion.amTwo.cancel(Companion.pnTwo)
            }
        }
    }
    private fun cancelAlarm(reminderTracker: ReminderTracker) {
        if (reminderTracker.deleteFlage == true) {
            Companion.amTwo =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            Companion.pnTwo = PendingIntent.getBroadcast(context, reminderTracker.id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Companion.amTwo.cancel(Companion.pnTwo)
        }
    }

    fun cancelAlarm(id: Int , context: Context) {
        am =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, updatRVreceiver::class.java)
        pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        am.cancel(pi)
    }
}