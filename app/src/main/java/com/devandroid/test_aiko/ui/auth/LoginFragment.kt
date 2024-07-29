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
import com.devandroid.test_aiko.databinding.FragmentLoginBinding
import com.devandroid.workoutschedule.helper.FirebaseHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentLoginBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            binding.progressBar.isVisible = true
            validateData()
        }

        binding.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.recoverAccountFragment)
        }
    }

    private fun validateData(){
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if(email.isNotEmpty()){
            if(password.isNotEmpty()){
                singInUser(email,password)
            }
        }else{
            Toast.makeText(requireContext(), "Informe seu Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun singInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    Toast.makeText(requireContext(),"Erro ao logar", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.isVisible = false
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}