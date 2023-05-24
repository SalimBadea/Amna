package com.salem.amna.domain.use_case.auth.validation

import android.util.Patterns
import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidateEmail @Inject constructor(){

    operator fun invoke(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage =  ResString.the_email_can_not_be_blank
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.that_is_not_a_valid_email
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}