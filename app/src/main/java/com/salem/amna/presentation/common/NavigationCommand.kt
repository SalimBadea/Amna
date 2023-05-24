package com.salem.amna.presentation.common

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    object Stop : NavigationCommand()
}