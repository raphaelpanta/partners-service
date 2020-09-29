package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Result

interface PartnerService {
    fun create(createPartnerRequest: CreatePartnerRequest): Result<CreatePartnerResponse, InvalidResult>
}