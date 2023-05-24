package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidatePhone @Inject constructor() {

    operator fun invoke(phone: String): ValidationResult {
        if (phone.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = ResString.the_phone_can_not_be_blank
            )
        } else {
            if (phone.length == 11) {
                if (phone.startsWith("010") || phone.startsWith("011") || phone.startsWith("012") || phone.startsWith(
                        "015"
                    )
                ) {
                    return ValidationResult(
                        successful = true
                    )
                } else {
                    return ValidationResult(
                        successful = false,
                        errorMessage = ResString.that_is_not_a_valid_phone
                    )
                }
            } else {
                return ValidationResult(
                    successful = false,
                    errorMessage = ResString.phone_must_be_11_number
                )
            }
        }
        return ValidationResult(
            successful = true
        )
    }
}