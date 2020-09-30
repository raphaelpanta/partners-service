package com.github.raphaelpanta.partners.service

import org.geojson.MultiPolygon
import org.geojson.Point
import java.util.*

data class PartnerResponse
(val id: String,
 val tradingName: String,
 val ownerName: String,
 val document: String,
 val coverageArea: MultiPolygon,
 val address: Point)