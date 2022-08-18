package com.example.therecipesecret

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.therecipesecret.databinding.ActivitySplashBinding


class Splash : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.splashImgAnimation.setAnimation(R.raw.food)
        binding.splashImgAnimation.playAnimation()

        handler= Handler(Looper.myLooper()!!)
        handler.postDelayed({
           val intent = Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       },5000)



    }
}