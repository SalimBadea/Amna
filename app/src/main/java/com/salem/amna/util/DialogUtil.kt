package com.salem.amna.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.widget.RelativeLayout
import com.salem.amna.R

class DialogUtil(context: Context) : Dialog(context) {

    init {
        window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val margin = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp)
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), margin)
        window?.setBackgroundDrawable(inset)
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
    }

    fun showDialog() {
        show()
    }

    fun hideDialog() {
        dismiss()
        cancel()
    }

    fun isShown(): Boolean = isShowing

}