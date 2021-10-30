package com.yefimoyevhen.pecode.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yefimoyevhen.pecode.R
import com.yefimoyevhen.pecode.databinding.FragmentScreenBinding
import com.yefimoyevhen.pecode.viewmodel.TestViewModel

private const val ARG_ORDER = "order"

class ScreenFragment(
) : Fragment() {
    var order: Int? = null

    private var _binding: FragmentScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getInt(ARG_ORDER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)
        _binding = FragmentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        viewModel.fragments.observe(viewLifecycleOwner) { fragments ->
            binding.btMinus.visibility = if (fragments.size <= 1 && order!! == 1) GONE else VISIBLE
        }
        binding.title.text = getString(R.string.create_notification)
        binding.text.text = "${getString(R.string.notification)}  $order"
        binding.notificationImgHolder.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_plus
            )
        )
        binding.createNotification.setOnClickListener {
            viewModel.sendNotification(order!!)
            binding.notificationCreated.visibility = VISIBLE
            (Handler(Looper.getMainLooper()).postDelayed({
                binding.notificationCreated.visibility = GONE
            }, 2000))
        }
        binding.tvFuter.text = order.toString()
        binding.btPlus.setOnClickListener { viewModel.addFragment() }
        binding.btMinus.setOnClickListener { viewModel.removeFragment() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(order: Int) =
            ScreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ORDER, order)
                }
            }
    }
}
