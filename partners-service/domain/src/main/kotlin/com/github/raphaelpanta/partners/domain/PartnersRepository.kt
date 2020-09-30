package com.github.raphaelpanta.partners.domain

import com.github.michaelbull.result.Result
import java.util.UUID

interface PartnersRepository {
    fun create(partner: Partner): Result<Partner, Throwable>

    fun find(id: UUID): Result<Partner?, Throwable>
}
