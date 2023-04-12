package com.salem.amna.presentation.ui.auth.verification_code

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentVerificationCodeBinding
import java.util.*

@AndroidEntryPoint
class VerificationCodeFragment : BaseFragment() {

//    private val viewModel: VerificationCodeViewModel by viewModels()
//    private val sharedViewModel: AuthSharedViewModel by activityViewModels()
    lateinit var time: CountDownTimer
    lateinit var sDuration: String
    private var timeIsPlayed = false
    private val binding by lazy {
        FragmentVerificationCodeBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View = binding.root


    override fun initVar() {
        setTimer(1000 * 60)
        initData()
    }


    override fun onEvent() {
        binding.backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.confirmButton.setOnClickListener {
            val code =
                "${binding.otpBox1.text}${binding.otpBox2.text}${binding.otpBox3.text}${binding.otpBox4.text}"
            if (code.length == 4) {
//                viewModel.onEvent(VerificationCodeEvent.Confirm(code))
            } else {
                showToast(getString(R.string.please_enter_valid_code))
            }
        }
        binding.resend.setOnClickListener {
            if (!timeIsPlayed) {
                setTimer(1000 * 60)
                //Toast.makeText(requireContext(), getString(R.string.the_code_has_been_resent), Toast.LENGTH_SHORT).show()
//                viewModel.resendRegisterCode()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.the_time_is_not_over_yet),
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

        binding.otpBox1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) binding.otpBox2.requestFocus()
            }
        })
        binding.otpBox2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) binding.otpBox3.requestFocus()
                else binding.otpBox1.requestFocus()
            }
        })
        binding.otpBox3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) binding.otpBox4.requestFocus()
                else binding.otpBox2.requestFocus()
            }
        })
        binding.otpBox4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.isEmpty()) binding.otpBox3.requestFocus()
            }
        })
    }

    override fun render() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.uiState.collect { state ->
//                if (state.isUpdate) {
//                    hideLoadingDialog()
//                    binding.code.text = state.result?.code.toString()
//                } else if (state.isSuccess) {
//                    hideLoadingDialog()
//                    findNavController().navigate(VerificationCodeFragmentDirections.actionVerificationCodeFragmentToVideoIntroFragment())
//                } else if (state.isLoading) {
//                    showLoadingDialog()
//                } else if (state.error.isNotBlank()) {
//                    hideLoadingDialog()
//                }
//            }
//        }
    }

    private fun initData() {
        lifecycleScope.launchWhenStarted {
//            sharedViewModel.registerSharedData.collect { data ->
//                viewModel.onEvent(VerificationCodeEvent.InitRegisterData(data))
//            }
        }
        lifecycleScope.launchWhenStarted {
//            sharedViewModel.code.collect { code ->
//                code?.let {
//                    binding.code.text = it.toString()
//                }
//            }
        }
    }


    private fun setTimer(i: Long) {
        timeIsPlayed = true
        time = object : CountDownTimer(i, 1000) {


            override fun onTick(l: Long) {

                sDuration = String.format(
                    Locale.ENGLISH, "%02d:%02d",
                    java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l) -
                            java.util.concurrent.TimeUnit.MINUTES.toSeconds(
                                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(
                                    l
                                )
                            ),

                    )

                binding.tvTimer.text = sDuration


            }

            override fun onFinish() {
                timeIsPlayed = false

            }
        }.start()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        time.cancel()
    }

    override fun navigate() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.navigation.collect { navigation ->
//                when (navigation) {
//                    NavigationCommand.Back -> {
//                        findNavController().popBackStack()
//                    }
//                    is NavigationCommand.ToDirection -> {
//                        findNavController().navigate(navigation.directions)
//                    }
//                }
//
//            }
//        }
    }

    override fun showEffect() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.effect.collect { effect ->
//                when (effect) {
//                    is UiEffect.ShowToast -> {
//                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }
//        }
    }
}