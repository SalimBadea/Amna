package com.salem.amna.presentation.ui.my_account.about_us

import com.salem.amna.data.models.response.pages.PagesResponse

data class PagesState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val result: PagesResponse? = null,
    val error: String = "",

    val message: String = "",
    val messageError: Int? = null,
)
