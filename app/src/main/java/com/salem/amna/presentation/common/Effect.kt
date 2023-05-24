package com.salem.amna.presentation.common    // Side effects
sealed class UiEffect {

    data class ShowToast(val message: String) : UiEffect()

    data class ShowToastFromResource(val id: Int) : UiEffect()

}
