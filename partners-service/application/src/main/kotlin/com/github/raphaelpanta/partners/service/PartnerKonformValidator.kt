package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult
import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxItems
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minItems
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

        CreatePartnerRequest::coverageArea {
            MultiPolygon::type required {
                addConstraint("Should be a valid MultiPolygon geoJSON type") {
                    it == "MultiPolygon"
                }
            }
        }

        CreatePartnerRequest::address {
            Point::type required {
                addConstraint("Should be a valid Point geoJson type") {
                    it == "Point"
                }
            }
            Point::coordinates required {
                maxItems(2) hint coordinatesMessage
                minItems(2) hint coordinatesMessage
            }
        }
    }

    override fun validate(request: CreatePartnerRequest) =
            when (val result = partnerValidation(request)) {
                is Valid -> Ok(request)
                is Invalid -> Err(
                        ValidationErrorResult(
                                result.errors
                                        .map {
                                            "${it.dataPath} - ${it.message}"
                                        }
                        )
                )
            }

    override fun validate(id: String): Result<UUID, InvalidResult> = try {
        Ok(UUID.fromString(id))
    } catch (e: IllegalArgumentException) {
        Err(ValidationErrorResult(listOf(".id - should be a valid UUID")))
    }

    companion object {
        const val brazilianLegalDocumentMessage = "should be in brazilian legal document. Ex: 1432132123891/0001"
        const val coordinatesMessage = "Should be a valid Point geoJson coordinates (long, lat)"
        const val maxStringLength = 255
        const val minStringLength = 1
    }
}
