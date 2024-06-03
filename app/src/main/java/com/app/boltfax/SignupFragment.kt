package com.app.boltfax

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.databinding.FragmentSignupBinding

class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate),
    View.OnClickListener {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()
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

    override fun onClick(v: View?) {
        when (v) {
            rootView.tvGetOTP -> {

            }

            rootView.btnSignUp -> {

            }
        }
    }

}