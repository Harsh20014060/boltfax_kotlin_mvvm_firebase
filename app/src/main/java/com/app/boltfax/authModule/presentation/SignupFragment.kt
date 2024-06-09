package com.app.boltfax.authModule.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.boltfax.R
import com.app.boltfax.authModule.viewModel.AuthViewModel
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.base.Resource
import com.app.boltfax.base.UserDataRequestModel
import com.app.boltfax.databinding.FragmentSignupBinding
import com.app.boltfax.mainModule.presentation.MainActivity
import com.google.firebase.auth.PhoneAuthProvider

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate),
    View.OnClickListener {


    private val authViewModel by lazy {
        AuthViewModel()
    }
    private var otpVerificationID = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        authViewModel.showLoader.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showLoader()
                }

                false -> {
                    hideLoader()
                }
            }
        }

        authViewModel.otpObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {
                    uiFieldBlock(isEnabled = true)
                    showSnackBar(
                        view = rootView.root, message = it.message ?: ""
                    )
                }

                is Resource.Error -> {
                    uiFieldBlock(isEnabled = true)
                    showSnackBar(
                        view = rootView.root, message = it.message ?: ""
                    )

                }

                is Resource.Success -> {
                    it.data?.let { token ->
                        otpVerificationID = token.first
                        resendToken = token.second
                    }

                    rootView.edOTPBox.isEnabled = true


                }
            }
        }

        authViewModel.signupObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )
                }

                is Resource.Error -> {
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )

                }

                is Resource.Success -> {
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )

                    startActivity(Intent(requireContext(), MainActivity::class.java))

                }
            }
        }

        authViewModel.verifyOTPObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {
                    uiFieldBlock(isEnabled = true)
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )
                }

                is Resource.Error -> {
                    uiFieldBlock(isEnabled = true)
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )

                }

                is Resource.Success -> {
                    authViewModel.signup(
                        UserDataRequestModel(
                            fullName = rootView.edUserName.text.toString(),
                            countryCode = "+${rootView.ccp.selectedCountryCode}",
                            contact = rootView.edContactNumber.text.toString(),
                            password = rootView.edPassword.text.toString(),
                            confirmPassword = rootView.edConfirmPassword.text.toString(),
                            termsAndCondition = rootView.cbTermsAndCondition.isChecked,
                        )
                    )

                }
            }
        }
    }

    private fun setupOnClickListener() {

        rootView.tvGetOTP.setOnClickListener(this)
        rootView.btnSignUp.setOnClickListener(this)


        rootView.tvSignIn.highlightText(
            getString(R.string.str_sign_in) to Pair(R.color.sky_blue) {

                findNavController().navigate(SignupFragmentDirections.actionNavSignupFragmentToNavLoginFragment())


            }
        )
    }

    private fun uiFieldBlock(isEnabled: Boolean = true) {
        rootView.apply {
            edUserName.isEnabled = isEnabled
            ccp.isEnabled = isEnabled
            edContactNumber.isEnabled = isEnabled
            edPassword.isEnabled = isEnabled
            edConfirmPassword.isEnabled = isEnabled
            cbTermsAndCondition.isEnabled = isEnabled
            tvGetOTP.isEnabled = !isEnabled
            btnSignUp.isEnabled = !isEnabled
            tvOTPTimer.visibility = if (isEnabled) View.VISIBLE else View.GONE
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            rootView.tvGetOTP -> {
                uiFieldBlock(isEnabled = false)

                authViewModel.generateOTP(
                    requireActivity(),
                    "+${rootView.ccp.selectedCountryCode}${rootView.edContactNumber.text}"
                )
            }

            rootView.btnSignUp -> {
                authViewModel.verifyOTP(
                    otpID = otpVerificationID,
                    otp = rootView.edOTPBox.text.toString()
                )

            }
        }
    }

}