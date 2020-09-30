package com.github.raphaelpanta.partners.service

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point

data class CreatePartnerRequest(
        val tradingName: String,
        val ownerName: String,
        val document: String,
        val coverageArea: MultiPolygon,
        val address: Point)
