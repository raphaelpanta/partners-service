package com.github.raphaelpanta.partners.domain

import org.geojson.MultiPolygon
import org.geojson.Point

class Partner(
        val id: Long? = null,
        val tradingName: String,
        val ownerName: String,
        val document: String,
        val coverageArea: MultiPolygon,
        val address: Point
) {
}