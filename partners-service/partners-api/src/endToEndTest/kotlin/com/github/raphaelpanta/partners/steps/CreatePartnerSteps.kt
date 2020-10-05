package com.github.raphaelpanta.partners.steps

import com.github.raphaelpanta.partners.CreatePartnerTest.server
import com.github.raphaelpanta.partners.drivers.UserDriver
import com.github.raphaelpanta.partners.fixtures.TestData.createPartnerResponse
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CreatePartnerSteps : En {

    private val user = UserDriver()

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

        Given("its coverage area is a MultiPolygon with coordinates") { coordinates: DataTable ->
            user.inputCoverageArea(coordinates.asLists(Float::class.java))
        }

        Given("its address is a Point with coordinates [{double}, {double}]") { long: Double, lat: Double ->
            user.inputAddress(long, lat)
        }

        Then("Partner should be created successfully") {
            val createPartner = user.requested()

            val response = server.processRequest(createPartner)

            expectThat(response.copy(id = "")).isEqualTo(createPartnerResponse)
        }

    }
}
