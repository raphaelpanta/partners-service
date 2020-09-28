package com.github.raphaelpanta.partners

import org.geojson.MultiPolygon
import org.geojson.Point

data class CreatePartnerResponse
(val id: Long,
 val tradingName: String,
 val ownerName: String,
 val document: String,
 val coverageArea: MultiPolygon,
 val address: Point)