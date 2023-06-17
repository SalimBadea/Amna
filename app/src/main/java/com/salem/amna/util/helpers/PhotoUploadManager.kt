package com.salem.amna.util.helpers

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore.Images
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object PhotoUploadManager {
    private lateinit var currentPhotoPath: String
    var list: MutableList<MultipartBody.Part> = arrayListOf()
    var list1: ArrayList<MultipartBody.Part> = arrayListOf()
    var image = MultipartBody.Part
    private lateinit var file: File
    private const val TAG = "PhotoUploadManager"

    fun uploadItemImages(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("images[]", file.name, fileReqBody)
    }

    fun uploadImageAfterTreatment(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("afterTreatmentImages[]", file.name, fileReqBody)
    }

    fun uploadTourImages(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("notesImages[]", file.name, fileReqBody)
    }

    fun uploadImageOrder(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("orderImage", file.name, fileReqBody)
    }

    fun uploadImageCounter(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("counterImages[]", file.name, fileReqBody)
    }

    fun uploadImageCar(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("carsImages[]", file.name, fileReqBody)
    }

    fun uploadFileOrder(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        return createFormData("orderFile", file.name, fileReqBody)
    }
    fun uploadFileTour(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        return createFormData("noticeFile", file.name, fileReqBody)
    }
    fun uploadTourImage(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("noticeImage", file.name, fileReqBody)
    }

    fun uploadImage(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("filePath: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("beforeTreatmentImages[]", file.name, fileReqBody)
    }

    fun getImageUri(context: Context, image: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val values = ContentValues()
        values.put(Images.Media.TITLE, "Title")
        val path: Uri = context.contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)!!
        return path
    }

    fun uploadWorkShopImage(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("profile_image: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("car_image", file.name, fileReqBody)
    }

    fun uploadMaintenanceImage(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        Log.i("MaintenanceImage: ", filePath!!)
        val fileReqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("malfunctionImages[]", file.name, fileReqBody)
    }

    //    fun createImageFile(status: String, mContext: Context): File? {
//        val storageDir: File? = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(
//            "mokafha_${status}_${DateTimePickerManager().getCurrentDate("dd_MM_yyyy")}", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = absolutePath
//        }
//        return image
//    }
    fun uploadListOfImages(part: MultipartBody.Part): MutableList<MultipartBody.Part> {
        list.add(part)
        Log.d(TAG, "uploadListOfImages: ${list.size}")
        return list
    }
    fun uploadListOfImages1(part: MultipartBody.Part): ArrayList<MultipartBody.Part> {
        list1.add(part)
        Log.d(TAG, "uploadListOfImages1: ${list1.size}")
        return list1
    }


    fun saveBitmap(status: String, finalBitmap: Bitmap): File {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
        val myDir = File("$root/mokafha")
        if (!myDir.exists())
            myDir.mkdirs()
        val file =
            File(myDir, "_${status}_${DateTimePickerManager().getCurrentDate("dd_MM_hh_mm")}.png")
        Log.d(TAG, "saveBitmap: ${file.path}")
        if (file.exists())
            file.delete()
        try {
            file.createNewFile()
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out)
            Log.d(TAG, "saveBitmap1: ${file.path}")
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "saveBitmap: ${e.message}")
        }
        return file
    }

}