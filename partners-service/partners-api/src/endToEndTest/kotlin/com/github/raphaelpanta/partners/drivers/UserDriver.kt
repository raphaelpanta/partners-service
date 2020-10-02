package com.github.raphaelpanta.partners.drivers

import com.github.raphaelpanta.partners.domain.MultiPolygon
import com.github.raphaelpanta.partners.domain.Point
import com.github.raphaelpanta.partners.service.CreatePartnerRequest


class UserDriver {
    private val map = HashMap<String, Any>()
    private val coverageArea = mutableListOf<List<List<List<Float>>>>()
    private lateinit var coordinates: Pair<Double, Double>

    fun inputTradingName(tradingName: String) {
        map["tradingName"] = tradingName
    }

    fun inputOwnerName(ownerName: String) {
        map["ownerName"] = ownerName
    }

    fun inputDocument(document: String) {
        map["document"] = document
    }

    fun inputAddress(long: Double, lat: Double) {
        map["address"] = Point(listOf(long, lat), "Point")
    }

    fun inputCoverageArea(coordinates: List<List<Float>>) {
        coverageArea.add(listOf(
                coordinates.map {
                    listOf(it[0], it[1])
                }
        ))
    }

    fun inputCoordinates(long: Double, lat: Double) {
        coordinates = long to lat
    }

    fun coordinates() = coordinates

    fun requested() = map.let {
        CreatePartnerRequest(
                it["tradingName"] as String,
                it["ownerName"] as String,
                it["document"] as String,
                MultiPolygon(coverageArea, "MultiPolygon"),
                it["address"] as Point
        )
    }
}