package com.github.raphaelpanta.partners.service

import com.github.raphaelpanta.partners.domain.Partner
import com.github.raphaelpanta.partners.domain.PartnersRepository


class PartnersAppService(private val partnersRepository: PartnersRepository) : PartnerService {
    override fun create(createPartnerRequest: CreatePartnerRequest): CreatePartnerResponse {
        return partnersRepository.create(createPartnerRequest.toPartner()).toResponse()
    }
}

fun CreatePartnerRequest.toPartner() =
        Partner(null, tradingName, ownerName, document, coverageArea, address)

fun Partner.toResponse() = CreatePartnerResponse(
        id ?: 0,
        tradingName,
        ownerName,
        document, coverageArea, address)
