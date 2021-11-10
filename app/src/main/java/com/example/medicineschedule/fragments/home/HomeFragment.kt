package com.example.medicineschedule.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.R
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_headrer_layout.view.*

@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    lateinit var viewModel: HomeRecViewModel


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
        currentUser = mAuth.currentUser!!
        binding.userNametextView.setText(currentUser?.displayName)
        Glide.with(this).load(currentUser?.photoUrl).into(binding.imageProfile)


//        val db=FirebaseFirestore.getInstance()
//        db.collection("users").get().addOnCompleteListener{
//            val result=StringBuffer()
//            if(it.isSuccessful){
//                for(document in it.result!!)
//                {nav
//                    result.append(document.data.getValue("Username"))
//                }
//                binding.userNametextView.setText(result)
//            }
//        }
//        checkUser()
        return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiViews()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
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
                Toast.makeText(context, "${it.size} are total records", Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchView2.setOnClickListener {

            val intent = Intent(context, DictionaryActivity::class.java)
            startActivity(intent)
//            var reminder = ReminderTracker("New","name","4:00","nottaken","3","before meal")
//            viewModel.onAddClick(reminder)

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
            R.id.addReminder->{
                val reminderIntent = Intent(getActivity(), AddMedication::class.java)
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
}