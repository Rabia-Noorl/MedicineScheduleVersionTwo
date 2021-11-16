package com.example.medicineschedule.fragments.home

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.R
import com.example.medicineschedule.classes.AlarmReceiver
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.example.medicineschedule.viewModels.MedicineRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_headrer_layout.view.*
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    lateinit var viewModel: HomeRecViewModel

    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent


    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth=FirebaseAuth.getInstance()
        creatNotificationChannel()

        currentUser = mAuth.currentUser!!
        binding.userNametextView.setText(currentUser?.displayName)
        Glide.with(this).load(currentUser?.photoUrl).into(binding.imageProfile)
        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()

        viewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            HomeRecViewModel::class.java)


        binding.viewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.viewModel= viewModel
        }
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            val intent = Intent(context, AddDose::class.java)
            startActivity(intent)
        }
        viewModel.allRemiders.observe(viewLifecycleOwner){
            it?.let {
                viewModel.addFun(it)
                Toast.makeText(context, "Alarm and notification on  ${it.size}  records", Toast.LENGTH_SHORT).show()
                var anim  = binding.homeLottieanim
                var getStarted  = binding.textView9
                var initialText  = binding.initialTV
                anim.isVisible = it.isEmpty()
                getStarted.isVisible = it.isEmpty()
                initialText.isVisible = it.isEmpty()
            }
        }

        binding.searchView2.setOnClickListener {

            setAlarm()
            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)

        }
    }
    private fun uiViews() {

        binding.addDose.setOnClickListener{
            onClick(it)
        }
        binding.addMeasurement.setOnClickListener{
            onClick(it)
        }
        binding.addReminder.setOnClickListener{
            onClick(it)
        }
        binding.addDoctor.setOnClickListener{
            onClick(it)
        }

    }
    fun onClick(v: View?) {
        when (v?.id) {

            R.id.addDose -> {
                val doseIntent = Intent(getActivity(), AddDose::class.java)
                startActivity(doseIntent)
            }

            R.id.addMeasurement -> {
                val measurementIntent = Intent(getActivity(), AddMeasurements::class.java)
                startActivity(measurementIntent)
            }
            R.id.addReminder -> {
                val reminderIntent = Intent(getActivity(), AddMedication::class.java)
                startActivity(reminderIntent)
            }
            R.id.addDoctor -> {
                val reminderIntent = Intent(getActivity(), AddDoctorActivity::class.java)
                startActivity(reminderIntent)
            }
        }
    }
    private fun checkUser() {
        //get current user
        val firebaseUser=mAuth.currentUser
        if(firebaseUser==null)
        {
            //user not logged in
            startActivity(Intent(getActivity(),SignIn::class.java))
        }
        else
        {
            //user logged in.. Get user Info
            val email=firebaseUser.email
            val userName=firebaseUser.displayName
            val profilePhoto=firebaseUser.photoUrl
            //set Email
//            binding.userNametextView.text=email
           binding.userNametextView.text=userName
            binding.imageProfile.setImageURI(profilePhoto)
        }

    }

    private fun setAlarm() {
  //      var t:Time =  Time(1637)

        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = calendar.time.hours
        calendar[Calendar.MINUTE] = calendar.time.minutes
        calendar[Calendar.SECOND] = 0+5
        calendar[Calendar.MILLISECOND] = 0

        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context,0,intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )
        Toast.makeText(context,"Alarm set successfully ${calendar.time}" , Toast.LENGTH_LONG).show()
    }
    private fun cancelAlarm() {

        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context,0,intent, 0)
        alarmManager.cancel(pendingIntent)

        Toast.makeText(context,"Alarm remove successfully" , Toast.LENGTH_SHORT).show()
    }
    private fun creatNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            val name = "ReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance  = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("AlarmId",name,importance)
            channel.description = description
            val notificationManager = requireContext().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}