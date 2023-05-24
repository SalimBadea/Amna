package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class IsNotEmpty @Inject constructor(){

    operator fun invoke(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.field_is_empty_error_message
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}