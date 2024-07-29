package com.devandroid.workoutschedule.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.databinding.FragmentRegisterBinding
import com.devandroid.workoutschedule.helper.FirebaseHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks(){
        binding.btnCriarConta.setOnClickListener{ validateData() }

        binding.imgbLogout.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun validateData(){
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        
        if(email.isNotEmpty()){
            if(password.isNotEmpty()){
                binding.progressBar.isVisible = true
                registerUser(email,password)
            }else{
                Toast.makeText(requireContext(), "Informe seu Email", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext(), "Informe sua Senha", Toast.LENGTH_LONG).show()
        }
    }

    private fun registerUser(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Erro ao criar Conta", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}