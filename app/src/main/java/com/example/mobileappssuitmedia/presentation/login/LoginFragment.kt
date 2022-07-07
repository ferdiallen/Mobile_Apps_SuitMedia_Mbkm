package com.example.mobileappssuitmedia.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mobileappssuitmedia.R
import com.example.mobileappssuitmedia.databinding.FragmentLoginBinding
import com.example.mobileappssuitmedia.utils.ResourceState

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding
    private val vm: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = Navigation.findNavController(view)
        binding.apply {
            checkPaliandrome.setOnClickListener {
                if (paliandromeField.text.isNotEmpty()) {
                    when (val words = vm.isPaliandromTrue(paliandromeField.text.toString())) {
                        is ResourceState.Success -> {
                            Toast.makeText(
                                requireContext(), words.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is ResourceState.Error -> {
                            Toast.makeText(
                                requireContext(), words.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            nextLogin.setOnClickListener {
                if (usernameField.text.isNotEmpty()) {
                    controller.navigate(
                        LoginFragmentDirections.actionLoginFragmentToMainFragment(
                            usernameField.text.toString()
                        )
                    )
                    return@setOnClickListener
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.field_name_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}