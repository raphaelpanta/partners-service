package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.service.CreatePartnerRequest
import com.github.raphaelpanta.partners.service.PartnerService

class PartnerController(private val partnerService: PartnerService) {

    fun create(createPartnerRequest: CreatePartnerRequest) = partnerService.create(createPartnerRequest)
}