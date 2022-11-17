package com.example.cryptocurrencyapp.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentCryptoListBinding
import com.example.cryptocurrencyapp.presentation.view.adapters.CryptoAdapter
import com.example.cryptocurrencyapp.presentation.view_model.AvailableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoListFragment : Fragment() {
    private lateinit var binding: FragmentCryptoListBinding
    private lateinit var adapter: CryptoAdapter

    private val coinViewModel by viewModels<AvailableViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CryptoAdapter { book ->
            findNavController().navigate(
                R.id.action_cryptoListFragment_to_detailCoinFragment,
                bundleOf("book" to book)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptoListBinding.inflate(layoutInflater, container, false)
        binding.rvAvailable.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinViewModel.getAvailableBook()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                coinViewModel.state.collect {
                    binding.progressBar.isVisible = it.isLoading
                    adapter.submitList(it.dataAvailable)
                }
            }
        }
    }
}
