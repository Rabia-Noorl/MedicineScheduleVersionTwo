package com.example.medicineschedule.fragments.medicines

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
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
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.classes.AlarmReceiver
import com.example.medicineschedule.classes.WorkerUpdateRV
import com.example.medicineschedule.classes.updatRVreceiver
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_frag_layout.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    lateinit var viewModel: HomeRecViewModel
    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent
    lateinit var alertdialogbuilder: AlertDialog.Builder

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    companion object {
        var statusFlag: Boolean = false
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        private fun setUpWorker() {
            val workRequest =
                PeriodicWorkRequest.Builder(WorkerUpdateRV::class.java, 10, TimeUnit.SECONDS)
                    .build()
            context?.let { WorkManager.getInstance(it).enqueue(workRequest) }
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
            val view = binding.root
            mAuth = FirebaseAuth.getInstance()
            creatNotificationChannel()
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
                it?.let {
                    val recViewList = ArrayList<ReminderTracker>()
                    it.forEach {
                        if (it.reminderType == "med" && !it.deleteFlage) {
                            recViewList.add(it)

                        }
                    }
                    viewModel.addFun(recViewList)
                    val anim = binding.homeLottieanim
                    val getStarted = binding.textView9
                    val initialText = binding.initialTV
                    anim.isVisible = recViewList.isEmpty()
                    getStarted.isVisible = recViewList.isEmpty()
                    initialText.isVisible = recViewList.isEmpty()
                    setAlarm(recViewList)
                    setAlarm(recViewList, "update")
                    cancelAlarm(recViewList)
                }
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
                //set Email
//            binding.userNametextView.text=email
                binding.userNametextView.text = userName
                binding.imageProfile.setImageURI(profilePhoto)
            }

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
                    intent.putExtra("type", "med")
                    intent.putExtra("name", "You have ${it.names} ${it.types} to take!")
                    pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, 0)
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )

                }
//                else if (calendar.time.before(date)) {
//                    if (it.status == "") {
//
//                        var reminder = ReminderTracker(
//                            "${it.reminderType}",
//                            "${it.types}",
//                            "${it.names}",
//                            "${it.dateTimes}",
//                            "Taken", "${it.quantity}",
//                            "${it.instructions}",
//                            "${it.strenght}",
//                            "${it.startDate}",
//                            "${it.endDate}",
//                            "${it.recodeCreationDate}",
//                            it.deleteFlage
//                        )
//                        reminder.id = it.id
//                        viewModel.onEditClick(reminder)
//                    }
//                }

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
                    //statusTV.setTextColor(R.color.doneColor)
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
                val intent = Intent(context, AddDose::class.java)
                intent.putExtra("med", reminderTracker)
                startActivity(intent)
            }
        }

    private fun setAlarm(list: List<ReminderTracker>, update:String) {
        val calendar = Calendar.getInstance()
            calendar[
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH],
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE]
            ] = 0
        calendar[Calendar.SECOND] = 2
                alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, updatRVreceiver::class.java)
        var bundle = Bundle()
        bundle.putSerializable("reminder", list as Serializable)
        intent.putExtra("bundle" , bundle)
                pendingIntent = PendingIntent.getBroadcast(context, 100, intent, 0)
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent)
        }
    }
