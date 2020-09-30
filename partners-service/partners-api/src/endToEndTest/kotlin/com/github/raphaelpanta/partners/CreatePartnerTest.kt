package com.github.raphaelpanta.partners

import io.cucumber.junit.platform.engine.Cucumber

@Cucumber
class CreatePartnerTest {
    companion object {
        val server = PartnerServiceDriver()
    }
}