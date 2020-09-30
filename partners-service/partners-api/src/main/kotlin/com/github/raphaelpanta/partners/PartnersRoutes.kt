package com.github.raphaelpanta.partners

import io.javalin.apibuilder.ApiBuilder.*

class PartnersRoutes(private val partnerController: PartnerController) {

    fun routes() = path("/partners") {
        post("/", partnerController::create)
        get("/:id", partnerController::find)
    }
}
