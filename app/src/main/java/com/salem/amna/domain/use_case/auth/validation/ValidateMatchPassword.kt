package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidateMatchPassword @Inject constructor() {

    operator fun invoke(password1: String, password2: String): ValidationResult {
        if (password2.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.password_can_not_be_empty
            )
        } else if (password1 != password2){
            return ValidationResult(
                successful = false,
                errorMessage = ResString.password_is_not_match
            )
        }
            return ValidationResult(
                successful = true
            )
    }
}