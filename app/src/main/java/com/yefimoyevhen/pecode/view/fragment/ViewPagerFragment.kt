package com.yefimoyevhen.pecode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.yefimoyevhen.pecode.databinding.FragmentViewPagerBinding
import com.yefimoyevhen.pecode.view.fragment.adapter.ViewPagerAdapter
import com.yefimoyevhen.pecode.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.updateLastPosition(position)
                }
            }
        )
       // binding.viewPager.currentItem = viewModel.lastPosition.value!!
        viewModel.fragments.observe(viewLifecycleOwner) { fragments ->
            adapter.submitList(fragments)
            binding.viewPager.currentItem = viewModel.lastPosition.value!!
        }
        viewModel.lastPosition.observe(viewLifecycleOwner) { binding.viewPager.currentItem = it }
        handleBackPressed()
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentItem: Int = binding.viewPager.currentItem
                    if (currentItem != 0) {
                        binding.viewPager.setCurrentItem(currentItem - 1, true)
                    } else {
                        if (isEnabled) {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}