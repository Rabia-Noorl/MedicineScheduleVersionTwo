package com.example.medicineschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.example.medicineschedule.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.reminder.setOnClickListener{
           if(binding.addReminder.visibility==VISIBLE) { binding.addReminder.visibility=GONE
               binding.view1.visibility= GONE
        }
else
{ binding.addReminder.visibility= VISIBLE
    binding.view1.visibility= VISIBLE
}
 }
        binding.imageView1.setOnClickListener{
            if(binding.addReminder.visibility==VISIBLE) {
                binding.addReminder.visibility=GONE
                binding.view1.visibility= GONE
            }
            else
            { binding.addReminder.visibility= VISIBLE
                binding.view1.visibility= VISIBLE
            }
        }
        binding.chanreminder.setOnClickListener{
            if(binding.changeReminder.visibility==VISIBLE) { binding.changeReminder.visibility=GONE
                binding.view2.visibility= GONE
            }
            else
            { binding.changeReminder.visibility= VISIBLE
                binding.view2.visibility= VISIBLE
            }
        }
        binding.imageView2.setOnClickListener{
            if(binding.changeReminder.visibility==VISIBLE) {
                binding.changeReminder.visibility=GONE
                binding.view2.visibility= GONE
            }
            else
            { binding.changeReminder.visibility= VISIBLE
                binding.view2.visibility= VISIBLE
            }
        }
        binding.deletereminder.setOnClickListener{
            if(binding.deleteReminder.visibility==VISIBLE) { binding.deleteReminder.visibility=GONE
                binding.view3.visibility= GONE
            }
            else
            { binding.deleteReminder.visibility= VISIBLE
                binding.view3.visibility= VISIBLE
            }
        }
        binding.imageView3.setOnClickListener{
            if(binding.deleteReminder.visibility==VISIBLE) {
                binding.deleteReminder.visibility=GONE
                binding.view3.visibility= GONE
            }
            else
            { binding.deleteReminder.visibility= VISIBLE
                binding.view3.visibility= VISIBLE
            }
        }
        binding.repo.setOnClickListener{
            if(binding.report.visibility==VISIBLE) { binding.report.visibility=GONE
                binding.view4.visibility= GONE
            }
            else
            { binding.report.visibility= VISIBLE
                binding.view4.visibility= VISIBLE
            }
        }
        binding.imageView4.setOnClickListener{
            if(binding.report.visibility==VISIBLE) {
                binding.report.visibility=GONE
                binding.view4.visibility= GONE
            }
            else
            { binding.report.visibility= VISIBLE
                binding.view4.visibility= VISIBLE
            }
        }
        binding.status.setOnClickListener{
            if(binding.mstatus.visibility==VISIBLE) { binding.mstatus.visibility=GONE
                binding.view5.visibility= GONE
            }
            else
            { binding.mstatus.visibility= VISIBLE
                binding.view5.visibility= VISIBLE
            }
        }
        binding.imageView5.setOnClickListener{
            if(binding.mstatus.visibility==VISIBLE) {
                binding.mstatus.visibility=GONE
                binding.view5.visibility= GONE
            }
            else
            { binding.mstatus.visibility= VISIBLE
                binding.view5.visibility= VISIBLE
            }
        }
        binding.upcomeReminders.setOnClickListener{
            if(binding.upcomReminders.visibility==VISIBLE) { binding.upcomReminders.visibility=GONE
                binding.view6.visibility= GONE
            }
            else
            { binding.upcomReminders.visibility= VISIBLE
                binding.view6.visibility= VISIBLE
            }
        }
        binding.imageView6.setOnClickListener{
            if(binding.upcomReminders.visibility==VISIBLE) {
                binding.upcomReminders.visibility=GONE
                binding.view6.visibility= GONE
            }
            else
            { binding.upcomReminders.visibility= VISIBLE
                binding.view6.visibility= VISIBLE
            }
        }
}}
