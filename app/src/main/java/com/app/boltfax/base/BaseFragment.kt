package com.app.boltfax.base

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.boltfax.R
import com.app.boltfax.util.SnackBarType

abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    lateinit var rootView: VB

    val baseActivity by lazy {
        activity as BaseActivity<*>
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflate(layoutInflater, container, false)

        // activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return rootView.root

    }

    fun TextView.highlightText(vararg targets: Pair<String, Pair<Int, (Context) -> Unit>>) {
        val spannable = SpannableStringBuilder(text)

        for ((targetText, properties) in targets) {
            val startIndex = text.indexOf(targetText)
            if (startIndex != -1) {
                val endIndex = startIndex + targetText.length
                // Apply color
                spannable.setSpan(
                    ForegroundColorSpan(properties.first),
                    startIndex,
                    endIndex,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                // Apply underline
                spannable.setSpan(
                    UnderlineSpan(),
                    startIndex,
                    endIndex,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                // Apply click listener
                spannable.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(view: View) {
                            properties.second.invoke(view.context)
                        }
                    },
                    startIndex,
                    endIndex,
                    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        movementMethod = LinkMovementMethod.getInstance()
        text = spannable
    }


    open val String.toastShort: Unit
        get() {
            Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
        }

    fun showLoader(text: Int = R.string.str_loading, isCancelable: Boolean = false) {
        baseActivity.showLoader(text, isCancelable)
    }

    fun hideLoader() {
        baseActivity.hideLoader()
    }

    fun showSnackBar(
        view: View, message: String = "",
        showAboveView: Boolean = false,
        showLongSnackBar: Boolean = false,
        snackBarType: SnackBarType = SnackBarType.SnackBarSuccess,
    ) {
        baseActivity.showSnackBar(view, message, showAboveView, showLongSnackBar, snackBarType)

    }

    fun showSnackBarWithAction(
        view: View,
        message: String = "",
        showAboveView: Boolean = false,
        showLongSnackBar: Boolean = false,
        snackBarType: SnackBarType = SnackBarType.SnackBarSuccess,
        buttonText: String = "",
        callback: (Any) -> Unit,
    ) {
        baseActivity.showSnackBarWithAction(
            view,
            message,
            showAboveView,
            showLongSnackBar,
            snackBarType,
            buttonText,
            callback = callback
        )


    }
}