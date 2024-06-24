package com.example.registrationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registrationapp.databinding.LayoutSigninBinding
import com.google.firebase.FirebaseApp

class SignInFragement : Fragment() {

    var _bindign : LayoutSigninBinding ?=null
    val binding get() = _bindign!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { FirebaseApp.initializeApp(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindign = LayoutSigninBinding.inflate(layoutInflater,container,false)
        binding.creteNewAccountbtn.setOnClickListener {
            (activity as LoginScreen).SwithToSignUpFragment()
        }
        return binding.root
    }

    companion object {
        val TAG = "Sign in Fragement"
    }
}