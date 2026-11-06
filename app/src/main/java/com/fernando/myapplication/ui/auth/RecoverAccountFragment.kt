package com.fernando.myapplication.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.fernando.myapplication.R
import com.fernando.myapplication.databinding.FragmentRecoverAccountBinding
import com.fernando.myapplication.util.initToolbar
import com.fernando.myapplication.util.showBottomSheet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : Fragment() {
    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        auth = Firebase.auth

        initListener()
    }

    private fun initListener(){
        binding.buttonRegister.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.Digiteemail.text.toString().trim()

        if(email.isNotBlank()){
            binding.progressBar.isVisible = true
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                binding.progressBar.isVisible = false
                if (task.isSuccessful) {
                    showBottomSheet(
                        titleDialog = R.string.text_title_warning,
                        titleButton = R.string.text_button_warning,
                        message = getString(R.string.text_message_recover_account_fragment),
                        onClick = { findNavController().popBackStack() }
                    )
                } else {
                    showBottomSheet(message = task.exception?.message ?: getString(R.string.error_generic))
                }
            }
        }else{
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
