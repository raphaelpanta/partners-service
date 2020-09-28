package com.github.raphaelpanta.partners

import io.cucumber.java8.En
import org.junit.jupiter.api.Assertions.assertEquals

class CreatePartnerSteps : En {

    init {
        Given("a partner named {string}") { string: String ->
            assertEquals(string, "ze de")
        }
    }
}