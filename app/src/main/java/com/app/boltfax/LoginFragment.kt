package com.app.boltfax

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()


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

            }

            rootView.tvGetOTP -> {

            }

            rootView.tvForgetPassword -> {

                findNavController().navigate(LoginFragmentDirections.actionNavLoginFragmentToNavForgetPasswordFragment())
            }

        }

    }

}