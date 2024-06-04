package com.app.boltfax.authModule.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.boltfax.R
import com.app.boltfax.authModule.viewModel.AuthViewModel
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.base.Resource
import com.app.boltfax.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    View.OnClickListener {

    private val authViewModel by lazy {
        AuthViewModel()
    }
    private var otpVerificationID = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()

        setupObserver()

        rootView.edMobileNo.setText("7728966450")
        rootView.edPassword.setText("Agent@123")
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

        authViewModel.loginObserver.observe(viewLifecycleOwner){
            when(it){
                is Resource.DataNotFound ->{
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

                }
            }
        }

        authViewModel.otpObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {
                    uiFieldVisibility(loginViaPassword = true)
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )
                }

                is Resource.Error -> {
                    uiFieldVisibility(loginViaPassword = true)
                    showSnackBar(
                        view = rootView.root,
                        message = it.message ?: ""
                    )

                }

                is Resource.Success -> {
                    otpVerificationID = it.data ?: ""

                }
            }
        }
    }

    private fun setupOnClickListener() {
        rootView.btnSignIn.setOnClickListener(this)
        rootView.tvForgetPassword.setOnClickListener(this)
        rootView.tvGetOTP.setOnClickListener(this)

        rootView.tvSignUp.highlightText(getString(R.string.str_sign_up) to Pair(R.color.sky_blue) {

            findNavController().navigate(LoginFragmentDirections.actionNavLoginFragmentToNavSignupFragment())


        })
    }

    override fun onClick(v: View?) {
        when (v) {
            rootView.btnSignIn -> {

                if (rootView.edOTPBox.isEnabled) {
                    authViewModel.verifyOTP(otpVerificationID, rootView.edOTPBox.text.toString())
                } else {
                    authViewModel.login(rootView.edMobileNo.text.toString(),password = rootView.edPassword.text.toString())
                }

            }

            rootView.tvGetOTP -> {
                uiFieldVisibility(loginViaPassword = false)
                authViewModel.generateOTP(requireActivity(), rootView.edMobileNo.text.toString())
            }

            rootView.tvForgetPassword -> {

                findNavController().navigate(LoginFragmentDirections.actionNavLoginFragmentToNavForgetPasswordFragment())
            }

        }

    }

    private fun uiFieldVisibility(loginViaPassword: Boolean = true) {
        rootView.edMobileNo.isEnabled = loginViaPassword
        rootView.edPassword.isEnabled = loginViaPassword

        rootView.edOTPBox.isEnabled = !loginViaPassword

    }

}