package com.salem.amna.presentation.ui.common

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.salem.amna.R
import com.salem.amna.databinding.FragmentImageDialogBinding
import com.salem.amna.util.showToast
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import java.lang.Exception


class ImageDialogFragment : BottomSheetDialogFragment() {

    private var camera_request_code: Int = 0
    private var gallery_request_code: Int = 0
    private var selectedWay: String = ""
    private var ba_status: String = ""
    private var mActivity: AppCompatActivity? = null
    private var mFragment: Fragment? = null
    private var mCurrentPhotoPath: String? = null

    private var binding: FragmentImageDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            ba_status = it.getString("BA_STATUS", "")
        }
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {

    }

    private fun initViews() {
        Nammu.init(context)
        if (!Nammu.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Nammu.askForPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, storagePermissionCallback
            )
        }
        if (!Nammu.checkPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {

            Nammu.askForPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE, storagePermissionCallback
            )

        }
        binding?.frCamera?.setOnClickListener {
            selectedWay = "camera";
            binding?.frCamera?.setBackgroundResource(R.drawable.bg_double_rounded_form)
            binding?.frGallery?.setBackgroundResource(R.drawable.bg_rounded_form_not_active)
        }

        binding?.frGallery?.setOnClickListener {
            selectedWay = "gallery";
            binding?.frCamera?.setBackgroundResource(R.drawable.bg_rounded_form_not_active)
            binding?.frGallery?.setBackgroundResource(R.drawable.bg_double_rounded_form)
        }

        binding?.btnCloseDialog?.setOnClickListener {
            dialog?.dismiss()
        }

        binding?.btnNext?.setOnClickListener {
            Nammu.init(context)
            if (ba_status == "product") {
                camera_request_code = REQUEST_PRODUCT_IMAGE_CAPTURE
                gallery_request_code = IMAGE_PRODUCT_REQUEST_CODE
            }

            if (selectedWay == "camera") {
                if (Nammu.checkPermission(android.Manifest.permission.CAMERA)) {
                    capturePicture()

                } else {
                    Nammu.askForPermission(
                        this,
                        android.Manifest.permission.CAMERA,
                        cameraPermissionCallback
                    )
                }

            } else {
                val intentForLoadingImage = Intent(Intent.ACTION_GET_CONTENT)
                intentForLoadingImage.type = "image/*"
                try {
                    if (intentForLoadingImage.resolveActivity(activity?.packageManager!!) != null) {
                        parentFragment?.startActivityForResult(
                            intentForLoadingImage,
                            gallery_request_code
                        )
                    }
                } catch (e: ActivityNotFoundException) {
                }
            }
            dismiss()
        }
    }

    private fun capturePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            if (takePictureIntent.resolveActivity(activity?.packageManager!!) != null) { // its always null
//                val photoFile: File
//                try {
//                    photoFile = createImageFile()
//                } catch (ex: IOException) {
//                    ex.printStackTrace()
//                    return
//                }
//
//                if (photoFile != null) {
//                    mCurrentPhotoPath = photoFile.absolutePath
//                    val photoUri: Uri = FileProvider.getUriForFile(
//                        mActivity ?: mFragment?.context!!,
//                        "com.orcav.amanetelmadina.fileprovider",
//                        photoFile
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                    parentFragment?.startActivityForResult(takePictureIntent, camera_request_code)
//                    parentFragment?.startActivityForResult(takePictureIntent, camera_request_code)
//                }
               parentFragment?.startActivityForResult(
                    takePictureIntent,
                    camera_request_code
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "capturePicture: ${e.message}")
        }
    }

    private val storagePermissionCallback: PermissionCallback = object : PermissionCallback {
        override fun permissionGranted() {
        }

        override fun permissionRefused() {
            showToast(getString(R.string.permission_error))
        }

    }
    private val cameraPermissionCallback: PermissionCallback = object : PermissionCallback {
        override fun permissionGranted() {
            capturePicture()
        }

        override fun permissionRefused() {
            showToast(getString(R.string.permission_error))
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        val IMAGE_PRODUCT_REQUEST_CODE: Int = 200
        val REQUEST_PRODUCT_IMAGE_CAPTURE: Int = 100

        val IMAGE_COUNTER_REQUEST_CODE: Int = 400
        val REQUEST_COUNTER_IMAGE_CAPTURE: Int = 109

        val IMAGE_CAR_REQUEST_CODE: Int = 401
        val REQUEST_CAR_IMAGE_CAPTURE: Int = 110

        val IMAGE_TOUR_REQUEST_CODE: Int = 209
        val REQUEST_TOUR_IMAGE_CAPTURE: Int = 108

        val IMAGE_AFTER_REQUEST_CODE: Int = 201
        val REQUEST_AFTER_IMAGE_CAPTURE: Int = 101

        val IMAGE_ORDER_REQUEST_CODE: Int = 205
        val REQUEST_ORDER_IMAGE_CAPTURE: Int = 102

        val REQUEST_ORDER_FILE_CAPTURE: Int = 107

        val IMAGE_PROB_REQUEST_CODE: Int = 202
        val REQUEST_BROB_IMAGE_CAPTURE: Int = 103

        val IMAGE_WORKSHOP_REQUEST_CODE: Int = 203
        val REQUEST_WORKSHOP_IMAGE_CAPTURE: Int = 105

        val IMAGE_MAINTENANCE_REQUEST_CODE: Int = 204
        val REQUEST_MAINTENANCE_IMAGE_CAPTURE: Int = 106

        private const val TAG = "ImageDialogFragment"

        @JvmStatic
        fun newInstance(beforeAfterStatus: String) =
            ImageDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("BA_STATUS", beforeAfterStatus)
                }
            }
    }
}