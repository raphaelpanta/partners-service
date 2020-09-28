package com.github.raphaelpanta.partners

import io.cucumber.java8.En
import org.geojson.MultiPolygon
import org.geojson.Point

class CreatePartnerSteps : En {
    private val map = HashMap<String, Any>()

    init {
        Given("a partner named {string}") { name: String ->
            map["tradingName"] = name
        }

        Given("its owner named {string}") { name: String ->
            map["ownerName"] = name
        }

        Given("its brazilian legal document is {string}") { document: String ->
            map["document"] = document
        }

        Given("its coverage area is a {string} with coordinates {string}") { polygonType: String,
                                                                             coordenates: String ->

            map["coverageArea"] = MultiPolygon()
        }

        Given("its address is a {string} with coordinates {string}") { polygonType: String, coordenates: String ->
            map["address"] = Point()
        }

        Then("Partner should be created successfully") {
            val request = map.let {
                CreatePartnerRequest(
                        it["tradingName"] as String,
                        it["ownerName"] as String,
                        it["document"] as String,
                        it["coverageArea"] as MultiPolygon,
                        it["address"] as Point
                )
            }


        }
    }
}