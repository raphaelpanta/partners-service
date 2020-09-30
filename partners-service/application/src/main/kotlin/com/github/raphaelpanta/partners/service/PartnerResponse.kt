package com.github.raphaelpanta.partners.service

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point

data class PartnerResponse
(val id: String,
 val tradingName: String,
 val ownerName: String,
 val document: String,
 val coverageArea: MultiPolygon,
 val address: Point)