package com.salem.amna.util.helpers

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class DateTimePickerManager {
    private val dateLiveData by lazy { MutableLiveData<String>() }
    private val timeLiveData by lazy { MutableLiveData<String>() }


    private fun getDate(
        pattern: String,
        selectedYear: Int,
        selectedMonth: Int,
        selectedDay: Int
    ): String {
        val calender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern)
        calender.set(Calendar.YEAR, selectedYear)
        calender.set(Calendar.MONTH, selectedMonth)
        calender.set(Calendar.DATE, selectedDay)
        return simpleDateFormat.format(calender.time)

    }


    private fun getTime(
        pattern: String,
        selectedHour: Int,
        selectedMinute: Int,
        selectedSecond: Int
    ): String {
        val calender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern)
        calender.set(Calendar.HOUR, selectedHour)
        calender.set(Calendar.MINUTE, selectedMinute)
        calender.set(Calendar.SECOND, selectedSecond)
        return simpleDateFormat.format(calender.time)


    }


    fun showDate(
        supportFragmentManager: FragmentManager,
        pattern: String
    ): MutableLiveData<String> {
        val now = Calendar.getInstance()
        DatePickerDialog.newInstance(
            { _, year, month, day ->
                dateLiveData.value = getDate(pattern, year, month, day)
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        ).apply {
            isCancelable = false
            show(supportFragmentManager, "DatePicker")
        }

        return dateLiveData
    }

    fun showTime(
        supportFragmentManager: FragmentManager,
        pattern: String
    ): MutableLiveData<String> {
        val now = Calendar.getInstance()
        TimePickerDialog.newInstance(
            { _, hour, minute, second ->
                timeLiveData.value = getTime(pattern, hour, minute, second)

            }, now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            false
        ).apply {
            isCancelable = false
            show(supportFragmentManager, "TimePicker")
        }
        return timeLiveData
    }

    fun getFormattedDate(pattern: String, originalPattern: String, date: String): String {
//        val originalDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.US)
        val originalDateFormat = SimpleDateFormat(originalPattern, Locale.ENGLISH)
        val parsedDate = originalDateFormat.parse(date)
        val simpleDateFormat = SimpleDateFormat(pattern,Locale.ENGLISH)
        return simpleDateFormat.format(parsedDate)
    }

    fun getCurrentDate(pattern: String): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(calendar.time)
    }


}