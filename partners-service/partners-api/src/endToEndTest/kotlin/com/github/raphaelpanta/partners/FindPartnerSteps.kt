package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.CreatePartnerTest.Companion.server
import com.github.raphaelpanta.partners.service.PartnerResponse
import io.cucumber.java8.En
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FindPartnerSteps : En {

    lateinit var id: String
    lateinit var response: PartnerResponse

    init {
        Given("that a partner named Adega da Cerveja - Pinheiros exists") {
            id = server.insertPartner("""{"tradingName": "Adega da Cerveja - Pinheiros",
                                      "ownerName": "Zé da Silva",
                                      "document": "1432132123891/0002",
                                      "coverageArea": { 
                                        "type": "MultiPolygon", 
                                        "coordinates": [
                                          [[[30.0, 20.0], [45.0, 40.0], [10.0, 40.0], [30.0, 20.0]]], 
                                          [[[15.0, 5.0], [40.0, 10.0], [10.0, 20.0], [5.0, 10.0], [15.0, 5.0]]]
                                        ]
                                      },
                                      "address": { 
                                        "type": "Point",
                                        "coordinates": [-46.57421, -21.785741]
                                      }
                                    }""")
        }

        Given("use its id") {
            response = server.queryPartner(id)
        }


        Then("return a partner {string}") { partner: String ->
            expectThat(response.tradingName) {
                isEqualTo(partner)
            }
        }

    }
}