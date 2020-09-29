package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository


class PartnersAppService(private val partnersRepository: PartnersRepository,
                         private val createPartnerValidator: CreatePartnerValidator
) : PartnerService {
    override fun create(createPartnerRequest: CreatePartnerRequest) =
            createPartnerValidator.validate(createPartnerRequest)
                    .map(CreatePartnerRequest::toPartner)
                    .flatMap {
                        partnersRepository.create(it)
                                .mapError { e -> InvalidResult(listOf(e.localizedMessage), type = ErrorType.Error) }
                    }
                    .map(Partner::toResponse)

}

fun CreatePartnerRequest.toPartner() =
        Partner(tradingName, ownerName, document, coverageArea, address)

fun Partner.toResponse() = CreatePartnerResponse(
        id,
        tradingName,
        ownerName,
        document,
        coverageArea,
        address)
