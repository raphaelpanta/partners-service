package com.github.raphaelpanta.partners.domain

import java.util.UUID

data class Partner(
        val tradingName: String,
        val ownerName: String,
        val document: String,
        val coverageArea: MultiPolygon,
        val address: Point,
        val id: String = UUID.randomUUID().toString()
)