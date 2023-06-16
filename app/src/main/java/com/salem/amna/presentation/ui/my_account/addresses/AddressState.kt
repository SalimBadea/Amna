package com.salem.amna.presentation.ui.my_account.addresses

import com.salem.amna.data.models.response.addresses.AddressesResponse

data class AddressState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val result: AddressesResponse? = null,
    val error: String = "",

    )
