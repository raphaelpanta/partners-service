package com.github.raphaelpanta.partners.domain

import org.geojson.MultiPolygon
import org.geojson.Point
import java.util.*

data class Partner(
        val tradingName: String,
        val ownerName: String,
        val document: String,
        val coverageArea: MultiPolygon,
        val address: Point,
        val id: String = UUID.randomUUID().toString()
)