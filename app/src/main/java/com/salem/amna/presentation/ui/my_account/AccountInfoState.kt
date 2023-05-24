package com.salem.amna.presentation.ui.my_account

import android.net.Uri
import com.salem.amna.data.models.post_body.AccountInfoBody
import com.salem.amna.data.models.response.accountinfo.AccountInfoResponse

data class AccountInfoState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isDeleted: Boolean = false,
    val result: AccountInfoResponse? = null,
    val error: String = "",
    val name: String = "",
    val slug: String = "",
    val nameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
    val gender: String = "",
    val birthDate: String = "",
    val avatar: String? = null,
    val avatarCollection: String? = null,
    val imageUri: Uri? = null,
) {

    fun toAccountInfoBody() = AccountInfoBody(
        email = if (email != result?.user?.email) email else result.user.email,
        phone = if (phone != result?.user?.phone) phone else result.user.phone,
        name = if (name != result?.user?.name) name else result.user.name,
        _method = "PATCH",
        image = if (avatarCollection != result?.user?.image) avatarCollection else result?.user?.image,

    )
}
