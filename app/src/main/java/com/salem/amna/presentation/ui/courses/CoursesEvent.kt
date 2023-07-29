package com.salem.amna.presentation.ui.courses

import com.salem.amna.data.models.response.categories.CategoriesResponse

sealed class CoursesEvent {

    object LoadCourses : CoursesEvent()
}