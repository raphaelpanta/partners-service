package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository
import com.github.raphaelpanta.partners.service.InvalidResult.InternalErrorResult


class PartnersAppService(private val partnersRepository: PartnersRepository,
                         private val createPartnerValidator: CreatePartnerValidator
) : PartnerService {
    override fun create(createPartnerRequest: CreatePartnerRequest) =
            createPartnerValidator.validate(createPartnerRequest)
                    .map(CreatePartnerRequest::toPartner)
                    .flatMap {
                        partnersRepository.create(it)
                                .mapError { e -> InternalErrorResult(listOf(e.localizedMessage)) }
                    }
                    .map(Partner::toResponse)

    override fun find(id: String): Result<PartnerResponse, InvalidResult> {
        TODO("Not yet implemented")
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
