package com.salem.amna.util

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.salem.amna.R
import com.salem.amna.data.models.ErrorResponse

fun Context.getStringByName(name: String): String {
    return getString(resources.getIdentifier(name, "string", packageName))
}

val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()


fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    @IdRes id: Int,
    addToBackStack: Boolean = false,
) {
    val trans = supportFragmentManager
        .beginTransaction()

        .replace(id, fragment)

    if (addToBackStack)
        trans.addToBackStack("")

    trans.commit()
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    @IdRes id: Int,
    addToBackStack: Boolean = false,
) {
    val trans = activity?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(id, fragment)

    if (addToBackStack)
        trans?.addToBackStack("")

    trans?.commit()
}
private var TOAST: Toast? = null
fun toast(context: Context, s: Any) {
    if (TOAST != null)
        TOAST!!.cancel()

    if (s is String)
        TOAST = Toast.makeText(context, s.toString(), Toast.LENGTH_SHORT)
    else if (s is Int)
        TOAST = Toast.makeText(context, s.toInt(), Toast.LENGTH_SHORT)

    TOAST!!.show()
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.invisibleView() {
    this.visibility = View.INVISIBLE
}

fun ImageView.setTint(@ColorRes color: Int?) {
    if (color == null) {
        ImageViewCompat.setImageTintList(this, null)
    } else {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, color))
        )
    }
}



fun share(context: Context, @StringRes shareBody: Int) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(
        Intent.EXTRA_SUBJECT, context.getString(R.string.app_name)
    )
    sharingIntent.putExtra(
        Intent.EXTRA_TEXT,
        String.format(
            context.getString(shareBody),
            context.getString(R.string.share_link) + context.packageName
        )
    )
    context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
}

fun ImageView.loadImageFromInternet(
    imageUrl: String?,
    errorPlaceholder: Drawable? = null,
    roundedCorner: Float = 0f
) {
    this.load(imageUrl) {
        crossfade(true)
        crossfade(500)
        RoundedCornersTransformation(roundedCorner)
        error(errorPlaceholder)
    }
}


fun getErrorResponse(response: String): ErrorResponse {
    return Gson().fromJson(response, ErrorResponse::class.java)
}

inline fun <reified T: Any> String.toKotlinObject(): T =
    Gson().fromJson(this, T::class.java)

fun Double.roundTheNumber(numberAfterDot: Int = 2): String {
    var result = ""
    result = "%.${numberAfterDot}f".format(this)
    if (result.endsWith("00"))
        result = result.dropLast(3)
    else if (result.endsWith(".0"))
        result = result.dropLast(2)
    return result

}

fun Double.roundLatLngNumber(numberAfterDot: Int = 6): String {
    var result = ""
    result = "%.${numberAfterDot}f".format(this)
    if (result.endsWith("00"))
        result = result.dropLast(3)
//    else if (result.endsWith(".0"))
//        result = result.dropLast(2)
    return result

}

fun View.debounceClick(debounceTime: Long = 1000L, action: () -> Unit) {
    setOnClickListener {
        when {
            tag != null && (tag as Long) > System.currentTimeMillis() -> return@setOnClickListener
            else -> {
                tag = System.currentTimeMillis() + debounceTime
                action()
            }
        }
    }
}

fun Context.colorList(id: Int): ColorStateList {
    return ColorStateList.valueOf(ContextCompat.getColor(this, id))
}


