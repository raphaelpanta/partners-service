package com.github.raphaelpanta.partners.domain

import com.github.michaelbull.result.Result

interface PartnersRepository {
    fun create(partner: Partner): Result<Partner, Throwable>
}