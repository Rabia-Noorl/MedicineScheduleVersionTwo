package com.example.medicineschedule.fragments.more

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.medicineschedule.*
import com.example.medicineschedule.database.ReminderTracker
import com.example.medicineschedule.databinding.FragmentHomeBinding
import com.example.medicineschedule.databinding.FragmentMoreBinding
import com.example.medicineschedule.fragments.medication.MedicationFragment
import com.example.medicineschedule.fragments.pharmacy.PharmacyFragment
import com.example.medicineschedule.viewModels.AppointmentViewModel
import com.example.medicineschedule.viewModels.HomeRecViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_dictionary.*

class MoreFragment : Fragment() {


    private lateinit var binding: FragmentMoreBinding
    lateinit var viewModel: AppointmentViewModel
    lateinit var alertdialogbuilder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val intent = Intent(getActivity(), AddDoctorActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            AppointmentViewModel::class.java)
        binding.appointmentViewModel = viewModel
        binding.let {
            it.lifecycleOwner = this
            it.appointmentViewModel = viewModel
        }

        viewModel.reminder.observe(viewLifecycleOwner) {reminder ->
            dialogueHandler(reminder)
        }

        viewModel.allRemiders.observe(viewLifecycleOwner){
            it?.let {
                var recViewList = ArrayList<ReminderTracker>()
                it.forEach{
                    if(it.reminderType == "doc" && !it.deleteFlage)
                    {
                        recViewList.add(it)
                    }
                }
                viewModel.addFun(recViewList)
                var anim  = binding.appoimtmentLottieanim
                var initialText  = binding.initialTV
                anim.isVisible = recViewList.isEmpty()
                initialText.isVisible = recViewList.isEmpty()
            }
        }



    }
    private fun dialogueHandler(reminderTracker: ReminderTracker){
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        var d : Dialog? = context?.let { Dialog(it) }
        d?.setContentView(R.layout.edit_record_dialoge)
        d!!.window?.setBackgroundDrawableResource(R.drawable.edit_text_design);
        d!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        d?.show()

        var name = d?.findViewById<TextView>(R.id.nameEditTV)
        var img = d?.findViewById<ImageView>(R.id.dialogeImgView)

        var updateBtn = d?.findViewById<Button>(R.id.updatebutton2)
        var deleteBtn = d?.findViewById<Button>(R.id.deletebutton3)

        img.setImageDrawable(
            ContextCompat.getDrawable(
                this.requireContext(), // Context
                R.drawable.doctor // Drawable
            )
        )
        name.setText(reminderTracker.names + reminderTracker.instructions)
        updateBtn.setOnClickListener {
            val intent = Intent(getActivity(), AddDoctorActivity::class.java)
            intent.putExtra("rem", reminderTracker)
            startActivity(intent)
        }
        deleteBtn.setOnClickListener{
            alertdialogbuilder= AlertDialog.Builder(this.requireContext())
            alertdialogbuilder.setCancelable(false)
            alertdialogbuilder.setTitle("Delete").setIcon(R.drawable.ic_delete)
                .setMessage("Are you sure you want to Delete it?").setCancelable(true).setPositiveButton("Yes"){dialogInterface,it->
//                    this.finish()
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
                    d?.cancel() }
                .setNegativeButton("No"){dialogInterface,it->
                    dialogInterface.cancel()
                }.show()
        }
    }}