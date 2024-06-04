package com.app.boltfax.authModule.presentation

import android.os.Bundle
import android.util.Log
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
                authViewModel.login("77289 66450")
            }

            rootView.tvGetOTP -> {

            }

            rootView.tvForgetPassword -> {

                findNavController().navigate(LoginFragmentDirections.actionNavLoginFragmentToNavForgetPasswordFragment())
            }

        }

    }

}