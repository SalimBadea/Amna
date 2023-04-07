package com.salem.amna.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.salem.amna.util.DialogUtil

abstract class BaseFragment : Fragment() {
    private val TAG_BASE = "BaseFragment"
    private lateinit var dialog: DialogUtil

    protected val baseActivity by lazy {
        activity as BaseActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return getRootView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeRequest()
    }

    abstract fun getRootView(): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = DialogUtil(baseActivity)
        initVar()
        onEvent()
        render()
        navigate()
        showEffect()

        Log.d(TAG_BASE, "onViewCreated: ")
    }

    open fun makeRequest() {
        // XD
    }

    abstract fun initVar()

    abstract fun onEvent()

    abstract fun render()

    abstract fun navigate()

    abstract fun showEffect()

    fun showToast(text: String, timing: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(baseActivity, text, timing).show()
    }

    fun clearErrorEditText(selectedText: TextInputLayout) {
        selectedText.error = null
        selectedText.isErrorEnabled = false
    }

    fun showLoadingDialog(){
        dialog.showDialog()
    }
    fun hideLoadingDialog(){
        dialog.hideDialog()
    }

}