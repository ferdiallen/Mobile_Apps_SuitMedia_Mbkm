package com.example.mobileappssuitmedia.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mobileappssuitmedia.MainActivity
import com.example.mobileappssuitmedia.R
import com.example.mobileappssuitmedia.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding as FragmentMainBinding
    private val args: MainFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = Navigation.findNavController(view)
        binding.apply {
            this.backArrow.setOnClickListener {
                (requireActivity() as MainActivity).onBackPressed()
            }
            args.userName?.let {
                nameUser.text = it
            }
            controller.currentBackStackEntry?.savedStateHandle
                ?.getLiveData<String>(getString(R.string.username_selected))
                ?.observe(viewLifecycleOwner){
                    selectedNameUser.text = it
                }
            selectUser.setOnClickListener {
                controller.navigate(
                    MainFragmentDirections
                        .actionMainFragmentToListUserFragment()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}