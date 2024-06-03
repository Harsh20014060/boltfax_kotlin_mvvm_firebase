package com.app.boltfax.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.app.boltfax.R
import com.app.boltfax.databinding.RawLoaderBinding
import com.app.boltfax.util.SnackBarType
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    PermissionActivity() {
    private val fireAuth by lazy {
        Firebase.auth
    }

    val rootView: VB by lazy { inflate(layoutInflater) }

    private val progressDialog by lazy {
        Dialog(
            this
        ).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(rootView.root)

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


    fun showLoader(text: Int = R.string.str_loading, isCancelable: Boolean = false) {
        progressDialog.dismiss()

        val binding = RawLoaderBinding.inflate(LayoutInflater.from(this))
        progressDialog.setContentView(binding.root)
        progressDialog.setCancelable(isCancelable)

        binding.tvLoaderText.text = getString(text)

        progressDialog.show()
    }

    fun hideLoader() {
        progressDialog.dismiss()
    }

    fun showSnackBar(
        view: View,
        message: String = "",
        showAboveView: Boolean = false,
        showLongSnackBar: Boolean = false,
        snackBarType: SnackBarType = SnackBarType.SnackBarSuccess,
    ) {

        if (message.isNotBlank() || snackBarType == SnackBarType.SnackBarInternet) {

            when (snackBarType) {
                SnackBarType.SnackBarSuccess -> {
                    Snackbar.make(
                        view, message, if (showLongSnackBar) {
                            Snackbar.LENGTH_LONG
                        } else {
                            Snackbar.LENGTH_SHORT
                        }
                    ).setAnchorView(
                        if (showAboveView) {
                            view
                        } else {
                            null
                        }
                    ).show()
                }

                SnackBarType.SnackBarError -> {

                }

                SnackBarType.SnackBarInternet -> {
                    Snackbar.make(
                        view, getString(R.string.err_internet), if (showLongSnackBar) {
                            Snackbar.LENGTH_LONG
                        } else {
                            Snackbar.LENGTH_SHORT
                        }
                    ).setAnchorView(
                        if (showAboveView) {
                            view
                        } else {
                            null
                        }
                    ).show()
                }
            }

        }

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

        if (message.isNotBlank() || snackBarType == SnackBarType.SnackBarInternet) {

            when (snackBarType) {
                SnackBarType.SnackBarSuccess -> {
                    Snackbar.make(
                        view, message, if (showLongSnackBar) {
                            Snackbar.LENGTH_LONG
                        } else {
                            Snackbar.LENGTH_SHORT
                        }
                    ).setAction(buttonText) {
                        callback.invoke(Unit)
                    }.setAnchorView(
                        if (showAboveView) {
                            view
                        } else {
                            null
                        }
                    ).show()
                }

                SnackBarType.SnackBarError -> {

                }

                SnackBarType.SnackBarInternet -> {
                    Snackbar.make(
                        view, getString(R.string.err_internet), if (showLongSnackBar) {
                            Snackbar.LENGTH_LONG
                        } else {
                            Snackbar.LENGTH_SHORT
                        }
                    ).setAction(getString(R.string.str_retry)) {
                        callback.invoke(Unit)
                    }.setActionTextColor(
                        ContextCompat.getColor(
                            this, R.color.secondary_color
                        )
                    ).setAnchorView(
                        if (showAboveView) {
                            view
                        } else {
                            null
                        }
                    ).show()
                }
            }

        }

    }


}