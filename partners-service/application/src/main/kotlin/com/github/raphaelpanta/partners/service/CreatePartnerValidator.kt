package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Result

interface CreatePartnerValidator {
    fun validate(request: CreatePartnerRequest): Result<CreatePartnerRequest, InvalidResult>
}