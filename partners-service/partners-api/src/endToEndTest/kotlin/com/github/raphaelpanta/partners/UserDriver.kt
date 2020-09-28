package com.github.raphaelpanta.partners

import org.geojson.LngLatAlt
import org.geojson.MultiPolygon
import org.geojson.Point
import org.geojson.Polygon

class UserDriver {
    private val map = HashMap<String, Any>()
    private val coverageArea = MultiPolygon()

    fun inputTradingName(tradindName: String) {
        map["tradingName"] = tradindName
    }

    fun inputOwnerName(ownerName: String) {
        map["ownerName"] = ownerName
    }

    fun inputDocument(document: String) {
        map["document"] = document
    }

    fun inputAddress(long: Double, lat: Double) {
        map["address"] = Point(long, lat)
    }

    fun inputCoverageArea(coordinates: List<List<Double>>) {
        coverageArea.add(Polygon(
                coordinates.map {
                    LngLatAlt(it[0], it[1])
                }
        ))
    }

    fun requested() = map.let {
        CreatePartnerRequest(
                it["tradingName"] as String,
                it["ownerName"] as String,
                it["document"] as String,
                coverageArea,
                it["address"] as Point
        )
    }
}