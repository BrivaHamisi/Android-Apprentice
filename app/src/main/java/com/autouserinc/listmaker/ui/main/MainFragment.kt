package com.autouserinc.listmaker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.autouserinc.listmaker.R
import com.autouserinc.listmaker.databinding.FragmentMainBinding
import org.intellij.lang.annotations.JdkConstants.ListSelectionMode


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.listRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.listRecyclerview.adapter = ListSelectionRecyclerViewAdapter()
        return binding.root
    }

}