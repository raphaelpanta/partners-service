package com.github.raphaelpanta.partners

import com.github.raphaelpanta.partners.drivers.PartnerServiceDriver
import io.cucumber.junit.platform.engine.Cucumber

@Cucumber
class CreatePartnerTest {
    companion object {
        val server = PartnerServiceDriver()
    }
}