package com.hasanzade.mviex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.hasanzade.mviex.databinding.FragmentCalcBinding
import kotlinx.coroutines.launch

class CalcFragment : Fragment() {

    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NumViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.num1.doAfterTextChanged {
            viewModel.onIntent(IntentCalc.Num1Val(it.toString()))
        }

        binding.num2.doAfterTextChanged {
            viewModel.onIntent(IntentCalc.Num2Val(it.toString()))
        }

        binding.buttonSum.setOnClickListener {
            viewModel.onIntent(IntentCalc.CalculateClicked)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> render(state) }
            }
        }
    }

    private fun render(state: NumState) {
        binding.resultText.text = state.result?.toString().orEmpty()

        if (state.error != null) {
            binding.errorText.visibility = View.VISIBLE
            binding.errorText.text = state.error
        } else {
            binding.errorText.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
