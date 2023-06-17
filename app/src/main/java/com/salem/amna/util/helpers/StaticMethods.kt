package com.salem.amna.util.helpers

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.salem.amna.R
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

object StaticMethods {
    const val TAG = "StaticMethods"
    val ERROR_IMAGE_PROFILE_MENU: Int = R.drawable.ic_image
    val PLACE_HOLDER_IMAGE_PROFILE_MENU: Int = R.drawable.ic_image
    var ERROR_IMAGE_CATEGORY: Int = R.drawable.ic_image
    var PLACE_HOLDER_IMAGE_CATEGORY: Int = R.drawable.ic_image
    var ERROR_IMAGE_CHATTING: Int = R.drawable.ic_image
    private const val dateTimeFormat = "yyyy-MM-dd"
    private const val dateTimeFormatWithDetails = "dd MMM yyyy, HH:mm"
    private const val timeFormatWith = "HH:mm"



    fun getLocalLanguage(context: Context): String {
        val locale: Locale
        locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return locale.language
        //        return "ar";
    }

    fun Context.setLocale(lang: String?) : Context {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(this.resources.configuration)
        config.setLocale(locale)
//        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        return createConfigurationContext(config)
    }


    fun setLocal(context: Context) : String {
        val myLocal = Locale(Locale.getDefault().toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.setDefault(Locale.forLanguageTag(Locale.getDefault().toString()))
        }
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocal
        res.updateConfiguration(conf, dm)
        conf.setLayoutDirection(myLocal)
        return  myLocal.language
    }


    val isRTL: Boolean
        get() = isRTL(Locale.getDefault())

    private fun isRTL(locale: Locale): Boolean {
        val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
    }

    fun getPart(filePath: String?, param: String?): MultipartBody.Part? {
        var body: MultipartBody.Part? = null
        try {
            val file = File(filePath)
            val requestFile: RequestBody = RequestBody.create(
                MultipartBody.FORM,
                file
            )
            body = MultipartBody.Part.createFormData(param!!, file.name, requestFile)
            return body
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return body
    }

    fun get64BaseStringImage(path: String?): String {
        val imageFile = File(path)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(imageFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bm: Bitmap = BitmapFactory.decodeStream(fis)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        //Base64.de
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun checkDevicePermission(
        context: Activity?,
        serviceName: String
    ): Int {   /* Return Device IMEI, this value can't be changed   */
        // ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   /*  Security Check for Android Marshemllo and Higher  */
//            if (ContextCompat.checkSelfPermission(context,
//                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    serviceName
                ) != PackageManager.PERMISSION_GRANTED
            ) {   // Should we show an explanation?
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!, arrayOf(serviceName),
                    1
                )
            } //  INNER OUTER IF
            else return 1
        } //  OUTER IF
        else {
            return 0
        }
        return -1
    }

    fun getFilePathFromBitmap(context: Context, bitmap: Bitmap): String? {
        //create a file to write bitmap data
        var path: String? = null
        try {
            val f = File(context.cacheDir, System.currentTimeMillis().toString() + ".png")
            f.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bitmapData = bos.toByteArray()
            val fos = FileOutputStream(f)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
            path = f.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return path
    }

    fun getRealPathFromURI(mContext: Context, contentUri: Uri?): String {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = mContext.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.getWidth(), source.getHeight(),
            matrix, true
        )
    }

    fun getImage(filePath: String?): Bitmap {
        val source: Bitmap = BitmapFactory.decodeFile(filePath)
        var ei: ExifInterface? = null
        try {
            ei = ExifInterface(filePath!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val orientation: Int = ei!!.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var rotatedBitmap: Bitmap? = null
        rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(source, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(source, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(source, 270f)
            ExifInterface.ORIENTATION_NORMAL -> source
            else -> source
        }
        return Bitmap.createScaledBitmap(rotatedBitmap, 1000, 1000, true)
    }

    fun getImageUri(inImage: Bitmap, context: Context): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 50, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "image", null)
        return Uri.parse(path)
    }

    fun getDate(time: Long, showDateDetails: Boolean): String {
        var date: Date? = null
        try {
            date = Date(time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val format: Format =
            SimpleDateFormat(if (showDateDetails) dateTimeFormatWithDetails else dateTimeFormat)
        return if (date != null) format.format(date) else time.toString()
    }

    fun getTime(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat(timeFormatWith)
        return format.format(date)
    }
}