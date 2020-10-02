package com.github.raphaelpanta.partners.steps

import com.github.raphaelpanta.partners.CreatePartnerTest.Companion.server
import com.github.raphaelpanta.partners.drivers.UserDriver
import io.cucumber.java8.En
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class NearestPartnerSteps : En {

    private val user = UserDriver()

    init {
        Given("partners registered in service") {
            server.fillDatabase()
        }

        When("user gives his location [{double}, {double}]") { long: Double, lat: Double ->
            user.inputCoordinates(long, lat)
        }

        Then("server localizes partner {string}") { tradingName: String ->
            user.coordinates()
                    .let(server::queryPartner)
                    .also {
                        expectThat(it) {
                            get { tradingName } isEqualTo tradingName
                        }
                    }
        }
    }
}