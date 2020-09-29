package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern

class CreatePartnerKonformValidator : CreatePartnerValidator {

    private val partnerValidation = Validation<CreatePartnerRequest> {
        CreatePartnerRequest::tradingName required {
            minLength(1)
            maxLength(255)
        }
        CreatePartnerRequest::ownerName required {
            minLength(1)
            maxLength(255)
        }
        CreatePartnerRequest::document required {
            pattern("\\d{13}/\\d{4}") hint "should be in brazilian legal document. Ex: 1432132123891/0001"
        }
    }

    override fun validate(request: CreatePartnerRequest): Result<CreatePartnerRequest, InvalidResult> =
            when (val result = partnerValidation(request)) {
                is Valid -> Ok(request)
                is Invalid -> Err(ValidationErrorResult(errors = result.errors.map { "${it.dataPath} - ${it.message}" }))
            }
}