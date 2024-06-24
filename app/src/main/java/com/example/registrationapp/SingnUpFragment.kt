package com.example.registrationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.registrationapp.data.User
import com.example.registrationapp.databinding.LayoutSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SingnUpFragment : Fragment() {
    private var _binding: LayoutSignupBinding?= null
    private val binding get() = _binding!!
    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = LayoutSignupBinding.inflate(inflater, container, false)
        binding.signupbtn.setOnClickListener { registorNewUser() }
        binding.suggestforsignin.setOnClickListener {
            (activity as LoginScreen).SwithToSigninFragment()
        }
        return binding.root
    }

    fun registorNewUser(){

        val username = binding.usernameView.text.toString()
        val email = binding.emailView.text.toString()
        val phomenumber = binding.mobile.text.toString()
        val password = binding.passwordView.text.toString()
        if (username.isEmpty() || email.isEmpty() || phomenumber.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"all fields are mandatory",Toast.LENGTH_SHORT).show()
        }else{
        database = FirebaseDatabase.getInstance().getReference("Users")
        val userid = database.push().key!!
        val newuser = User(uid = userid,name = username, email = email, phone = phomenumber, password = password)
        binding.singnupLayoutView.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.child(userid).setValue(newuser).await()
                withContext(Dispatchers.Main) {
                    binding.singnupLayoutView.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    clearUserInput()
                    Toast.makeText(context, "new user has been created", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.singnupLayoutView.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    clearUserInput()
                    Toast.makeText(context, "somthing went wrong!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        }
    }

    fun clearUserInput(){
        binding.usernameView.text?.clear()
        binding.emailView.text?.clear()
        binding.mobile.text?.clear()
        binding.passwordView.text?.clear()
    }

    companion object {
        val TAG = "Sign up Fragment"
    }
}