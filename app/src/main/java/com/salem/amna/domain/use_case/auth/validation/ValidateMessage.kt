package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidateMessage @Inject constructor(){

    operator fun invoke(message: String): ValidationResult {
        if (message.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.message_is_empty_error_message
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}