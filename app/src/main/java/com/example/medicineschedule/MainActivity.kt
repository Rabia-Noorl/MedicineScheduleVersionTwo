package com.example.medicineschedule

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.transition.Scene
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import com.airbnb.lottie.LottieAnimationView
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.txtmedicine)
//        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.animationView1)
        text.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim))
//        lottieAnimationView.animate().translationY(1600.0F).setDuration(1000).setStartDelay(4000)
//        text.animate().translationY(1600.0F).setDuration(1000).setStartDelay(4000)
//
        val handler = Handler();
        handler.postDelayed(Runnable() {
            run() {
                // TODO: Your application init goes here.

                val intent = Intent(this, SignInWithGoogle::class.java);
                this.startActivity(intent);
                this.finish();
            }
        }, 3000);

        }

}


//        Handler().postDelayed(Runnable {
//            fun run()
//                val Intent = Intent(this,SignUp::class.java)
//                startActivity(Intent)
//        }
//
//        },5000)
//    }
//    fun nextScreen(v: View)
//    {
//        val Intent = Intent(this,SignUp::class.java)
//        startActivity(Intent)
//    }
