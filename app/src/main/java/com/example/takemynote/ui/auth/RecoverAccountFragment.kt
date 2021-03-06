package com.example.takemynote.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.takemynote.R
import com.example.takemynote.databinding.FragmentRecoverAccountBinding
import com.example.takemynote.databinding.FragmentRegisterBinding
import com.example.takemynote.helper.FirebaseHelper
import com.example.takemynote.ui.adapter.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    //Declaração referencia firebase
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
        //inicializar auth
        auth = Firebase.auth

        initClicks()
    }

    private fun initClicks() {
            binding.btnEnviar.setOnClickListener { validateData() }
        }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
           if (email.isNotEmpty()) {

               hideKeyboard()

               binding.progressBar.isVisible = true

                recoverAccountUser(email)

            } else {
                Toast.makeText(requireContext(), "Informe seu e-mail.", Toast.LENGTH_SHORT).show()
            }
        }

        private fun recoverAccountUser(email: String) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Acabamos de enviar um link para seu e-mail", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            FirebaseHelper.validError(task.exception?.message ?: ""),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.isVisible = false
                    }
                }
        }

       override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }

