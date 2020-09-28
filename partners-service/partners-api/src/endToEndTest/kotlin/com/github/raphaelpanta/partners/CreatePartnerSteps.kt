package com.github.raphaelpanta.partners

import io.cucumber.java8.En

class CreatePartnerSteps() : En {

    private val user = UserDriver()

    private val server = PartnerServiceDriver()

    init {
        Given("a partner named {string}") { name: String ->
            user.inputTradingName(name)
        }

        Given("its owner named {string}") { name: String ->
            user.inputOwnerName(name)
        }

        Given("its brazilian legal document is {string}") { document: String ->
            user.inputDocument(document)
        }

        Given("its coverage area is a {string} with coordinates {string}") { polygonType: String,
                                                                             coordinates: String ->
            user.inputCoverageArea(coordinates)
        }

        Given("its address is a {string} with coordinates [{double}, {double}]") { polygonType: String, long: Double, lat: Double ->
            user.inputAddress(long, lat)
        }

        Then("Partner should be created successfully") {
            val createPartner = user.requested()

            server.processRequest(createPartner)
        }
    }
}