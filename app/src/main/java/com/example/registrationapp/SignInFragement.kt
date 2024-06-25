package com.example.registrationapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.registrationapp.data.User
import com.example.registrationapp.databinding.LayoutSigninBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInFragement : Fragment() {

    var _bindign : LayoutSigninBinding ?=null
    lateinit var auth: FirebaseAuth
    lateinit var database : DatabaseReference
    lateinit var currentuser : User
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
        binding.logininputLayout.visibility = View.VISIBLE
        binding.creteNewAccountbtn.setOnClickListener {
            (activity as LoginScreen).SwithToSignUpFragment()
        }

        binding.loginbtn.setOnClickListener {
            val username = binding.usernameView.text.toString()
            val password = binding.passwordView.text.toString()
            auth = FirebaseAuth.getInstance()
            loginTouser(username, password)
        }
        return binding.root
    }

    fun loginTouser(emailid: String, password: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        binding.logininputLayout.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val snapshot = database.get().await()
                var userFound = false;
                for(userSnapshot in snapshot.children ){

                    currentuser = userSnapshot.getValue(User::class.java)!!

                    if(currentuser !== null && currentuser.email == emailid && currentuser.password == password){
                        userFound = true;
                        withContext(Dispatchers.Main){
                            Toast.makeText(context,"Login successful",Toast.LENGTH_SHORT).show()
                            val intent = Intent(context,MainActivity::class.java)
                            binding.progressCircular.visibility = View.GONE
                            startActivity(intent)
                            activity?.finish()
                        }
                        break
                    }
                }

                if(!userFound){
                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"user not found",Toast.LENGTH_SHORT).show()
                        binding.logininputLayout.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    binding.logininputLayout.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(context,"something went wrong!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        val TAG = "Sign in Fragement"
    }
}