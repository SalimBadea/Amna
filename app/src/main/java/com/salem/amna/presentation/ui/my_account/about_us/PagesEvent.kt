package com.salem.amna.presentation.ui.my_account.about_us

sealed class PagesEvent {

    object GetAbout : PagesEvent()
    object GetPrivacy: PagesEvent()
}