package com.github.raphaelpanta.partners.service

import com.github.michaelbull.result.Result

interface PartnerService {
    fun create(createPartnerRequest: CreatePartnerRequest): Result<PartnerResponse, InvalidResult>

    fun find(id: String): Result<PartnerResponse, InvalidResult>

    fun nearestPartnerForLocation(coordinates: Pair<Float, Float>): Result<PartnerResponse, InvalidResult>
}
