package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.*
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.github.raphaelpanta.partners.service.InvalidResult.InternalErrorResult
import com.github.raphaelpanta.partners.service.InvalidResult.ValidationErrorResult


class PartnersAppService(private val partnersRepository: PartnersRepository,
                         private val partnerValidator: PartnerValidator
) : PartnerService {
    override fun create(createPartnerRequest: CreatePartnerRequest) =
            partnerValidator.validate(createPartnerRequest)
                    .map(CreatePartnerRequest::toPartner)
                    .flatMap {
                        partnersRepository.create(it)
                                .mapError { e -> InternalErrorResult(listOf(e.localizedMessage)) }
                    }
                    .map(Partner::toResponse)

    override fun find(id: String): Result<PartnerResponse, InvalidResult> {
        return partnerValidator.validate(id)
                .flatMap {
                    partnersRepository.find(it).mapError { e -> InternalErrorResult(listOf(e.localizedMessage)) }
                }.flatMap {
                    it?.let { Ok(it.toResponse()) }
                            ?: Err(ValidationErrorResult(listOf("Partner not found")))
                }
    }

}

fun CreatePartnerRequest.toPartner() =
        Partner(tradingName, ownerName, document, coverageArea, address)

fun Partner.toResponse() = PartnerResponse(
        id,
        tradingName,
        ownerName,
        document,
        coverageArea,
        address)
