package com.salem.amna.base

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.salem.amna.R
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.util.Constants.ENGLISH
import com.salem.amna.util.DialogUtil
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private val dialogUtil by lazy {
        DialogUtil(this)
    }
    private var lang: String? = null
    private var lat: String? = null
    private var long: String? = null

    @Inject
    lateinit var preference: LocalePreference

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(this).create()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //setStatusTransparent(window, true)

        initVar()
        onEvent()
        observeData()
    }

    abstract fun initVar()
    abstract fun onEvent()
    abstract fun observeData()

    fun handleDialog(dialogShowStatus: Boolean) {
        if (dialogShowStatus)
            dialogUtil.showDialog()
        else if (!dialogShowStatus && dialogUtil.isShown())
            dialogUtil.hideDialog()
    }

    fun hideDialog() {
        dialogUtil.hideDialog()
    }

    fun showDialog() {
        dialogUtil.showDialog()
    }

    fun openMap(latitude: Double, longitude: Double, locationName: String) {
        val geoUri = "http://maps.google.com/maps?q=loc:$latitude,$longitude ($locationName)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
        startActivity(intent)
    }

    fun showErrorMessage(
        title: String?,
        errorMsg: String?,
        showCancelButton: Boolean = true,
        successAction: (() -> Unit)? = null
    ) {
        alertDialog.setTitle(title)
        alertDialog.setMessage(errorMsg)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)
        ) { dialog: DialogInterface, _: Int ->
            if (successAction == null)
                dialog.dismiss()
            else {
                dialog.dismiss()
                successAction.invoke()
            }
        }

        if (showCancelButton) {
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel)
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }
        alertDialog.show()
    }

    open fun createMessage(
        title: String?,
        errorMsg: String?
    ): AlertDialog? {
        alertDialog.setTitle(title)
        alertDialog.setMessage(errorMsg)
        return alertDialog
    }

    fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    fun showToast(text: String, timing: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, timing).show()
    }

    fun updateLanguage() {
        GlobalScope.launch {
            lang = preference.getLanguage()
        }

        Lingver.getInstance().setLocale(baseContext, getLocale(lang!!))
    }

    private fun getLocale(language: String): Locale =
        if (language == ENGLISH) Locale("en", "US") else Locale("ar", "EG")

}