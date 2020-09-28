package com.github.raphaelpanta.partners

import org.geojson.MultiPolygon
import org.geojson.Point

class UserDriver {
    private val map = HashMap<String, Any>()

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

    fun inputCoverageArea(coordinates: Any) {
        map["coverageArea"] = MultiPolygon()
    }

    fun requested() = map.let {
        CreatePartnerRequest(
                it["tradingName"] as String,
                it["ownerName"] as String,
                it["document"] as String,
                it["coverageArea"] as MultiPolygon,
                it["address"] as Point
        )
    }
}