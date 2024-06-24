package com.example.registrationapp

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import com.example.registrationapp.databinding.ActivityLoginScreenBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference


class LoginScreen : AppCompatActivity() {
    lateinit var binding : ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id,SignInFragement())
                .commit()
        }
    }

    fun SwithToSigninFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,SignInFragement())
            .addToBackStack(null)
            .commit()
    }

    fun SwithToSignUpFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,SingnUpFragment())
            .addToBackStack(null)
            .commit()
    }

}