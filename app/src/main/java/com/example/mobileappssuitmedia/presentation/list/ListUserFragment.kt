package com.example.mobileappssuitmedia.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileappssuitmedia.R
import com.example.mobileappssuitmedia.databinding.FragmentListUserBinding
import com.example.mobileappssuitmedia.mediator.RecycleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@ExperimentalPagingApi
@AndroidEntryPoint
class ListUserFragment : Fragment() {
    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding as FragmentListUserBinding
    private val vm: ListUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val controller = Navigation.findNavController(view)
        val recycleAdapter = RecycleAdapter {
            controller.previousBackStackEntry?.savedStateHandle?.set(
                getString(R.string.username_selected),
                it
            )
            controller.popBackStack()
        }
        binding.apply {
            swipeContainer.setOnRefreshListener {
                recycleAdapter.refresh()
            }
            backArrow.setOnClickListener {
                controller.popBackStack()
            }
            listUserData.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recycleAdapter
                recycleAdapter.addLoadStateListener { state ->
                    if (state.append.endOfPaginationReached) {
                        if (recycleAdapter.itemCount < 1) {
                            emptyState.visibility = View.VISIBLE
                            swipeContainer.isRefreshing = false
                            return@addLoadStateListener
                        }
                        swipeContainer.isRefreshing = false
                        emptyState.visibility = View.GONE
                    }
                }
                lifecycleScope.launchWhenCreated {
                    vm.pagerData.collectLatest {
                        recycleAdapter.submitData(it)
                    }
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}