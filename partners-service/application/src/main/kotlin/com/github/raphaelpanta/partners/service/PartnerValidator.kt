package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Result
import java.util.*

interface PartnerValidator {
    fun validate(request: CreatePartnerRequest): Result<CreatePartnerRequest, InvalidResult>

    fun validate(id: String): Result<UUID, InvalidResult>
}