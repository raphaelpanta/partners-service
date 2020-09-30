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
import java.util.UUID

class PartnerKonformValidator : PartnerValidator {

    private val partnerValidation = Validation<CreatePartnerRequest> {
        CreatePartnerRequest::tradingName required {
            minLength(minStringLength)
            maxLength(maxStringLength)
        }
        CreatePartnerRequest::ownerName required {
            minLength(minStringLength)
            maxLength(maxStringLength)
        }
        CreatePartnerRequest::document required {
            pattern("\\d{13}/\\d{4}") hint brazilianLegalDocumentMessage
        }
    }

    override fun validate(request: CreatePartnerRequest): Result<CreatePartnerRequest, InvalidResult> =
            when (val result = partnerValidation(request)) {
                is Valid -> Ok(request)
                is Invalid -> Err(
                        ValidationErrorResult(
                                result.errors
                                        .map {
                                            "${it.dataPath} - ${it.message}"
                                        }
                        ))
            }

    override fun validate(id: String): Result<UUID, InvalidResult> = try {
        Ok(UUID.fromString(id))
    } catch (e: IllegalArgumentException) {
        Err(ValidationErrorResult(listOf(".id - should be a valid UUID")))
    }

    companion object {
        const val brazilianLegalDocumentMessage = "should be in brazilian legal document. Ex: 1432132123891/0001"
        const val maxStringLength = 255
        const val minStringLength = 1
    }
}
