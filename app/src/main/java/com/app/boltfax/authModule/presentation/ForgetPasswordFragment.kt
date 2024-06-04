package com.app.boltfax.authModule.presentation

import android.os.Bundle
import android.view.View
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment :
    BaseFragment<FragmentForgetPasswordBinding>(FragmentForgetPasswordBinding::inflate),
    View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()
    }


    private fun setupOnClickListener() {

        rootView.tvGetOtp.setOnClickListener(this)
        rootView.tvBackToLogin.setOnClickListener(this)
        rootView.tvCreateNewAccount.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            rootView.tvGetOtp -> {

            }

            rootView.tvBackToLogin -> {

            }

            rootView.tvCreateNewAccount -> {

            }
        }
    }

}