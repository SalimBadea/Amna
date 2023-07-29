package com.salem.amna.data.models.response.courses

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.common.UserModel

data class CoursesResponse (

    @field:SerializedName("courses")
    val courses: MutableList<CoursesModel>? = null,
)

