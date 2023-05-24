package com.salem.amna.domain.use_case.auth.validation

import com.salem.amna.util.ResString
import javax.inject.Inject

class ValidateEmailOrPassword @Inject constructor(){

    operator fun invoke(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage =  ResString.the_email_can_not_be_blank
            )
        }
////        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
////            return ValidationResult(
////                successful = false,
////                errorMessage = ResString.that_is_not_a_valid_email
////            )
////        }
//        else{
//            if(email.length >=10){
//                if(email.startsWith("05")){
//                    return ValidationResult(
//                        successful = true
//                    )
//                }else{
//                    return ValidationResult(
//                        successful = false,
//                        errorMessage = ResString.phone_must_be_start_with_05
//                    )
//                }
//            }else{
//                return ValidationResult(
//                    successful = false,
//                    errorMessage = ResString.phone_must_be_10_number
//                )
//            }
//        }
        return ValidationResult(
            successful = true
        )
    }
}