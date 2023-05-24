package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidateName @Inject constructor(){

    operator fun invoke(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.user_name_is_empty_error_message
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}