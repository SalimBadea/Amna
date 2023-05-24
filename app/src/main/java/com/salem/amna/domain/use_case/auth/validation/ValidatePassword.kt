package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.password_can_not_be_empty
            )
        } else if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.password_must_atleast_6_character
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}